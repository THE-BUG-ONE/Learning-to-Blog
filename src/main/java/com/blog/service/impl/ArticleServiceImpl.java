package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Article;
import com.blog.entity.dao.ArticleTag;
import com.blog.entity.dao.Category;
import com.blog.entity.dao.Tag;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;
import com.blog.mapper.ArticleMapper;
import com.blog.service.*;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.PageUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Lazy
    @Resource
    private CategoryService categoryService;

    @Lazy
    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private ArticleTagService articleTagService;

    @Lazy
    @Resource
    private TagService tagService;

    @Lazy
    @Resource
    private SiteConfigService siteConfigService;

    @Resource
    private PageUtils pageUtils;

    @Resource
    private WebUtils webUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageResult<ArticleHomeResp> getArticleHomeList(PageReq req) {
        pageUtils.setPage(req);
        List<ArticleHomeResp> articleHomeRespList = baseMapper.getArticleHomeList(req);
        return new PageResult<>(articleHomeRespList.size(), articleHomeRespList);
    }

    @Override
    public List<ArticleRecommendResp> getArticleRecommendList() {
        //文章为推荐、未删除、公开
        return BeanCopyUtils.copyBeanList(
                lambdaQuery().eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC).list()
                , ArticleRecommendResp.class);
    }

    @Override
    public ArticleResp getArticle(Integer articleId) {
        //浏览量+1
        stringRedisTemplate.opsForZSet().incrementScore(
                SystemConstants.ARTICLE_VIEW_COUNT , String.valueOf(articleId), 1L);
        //填充article属性
        return baseMapper.getArticle(articleId)
                .setNextArticle(selectOtherArticle(articleId, true))
                .setPrevArticle(selectOtherArticle(articleId, false))
                .setLikeCount(Optional.ofNullable(
                        stringRedisTemplate.opsForZSet().score(
                                SystemConstants.ARTICLE_LIKE_COUNT, String.valueOf(articleId)))
                        .orElse(0D).longValue())
                .setViewCount(Optional.ofNullable(
                        stringRedisTemplate.opsForZSet().score(
                                SystemConstants.ARTICLE_VIEW_COUNT, String.valueOf(articleId)))
                        .orElse(1D).longValue());
    }

    @Override
    public List<ArticleSearchResp> getArticleSearchList(String keyword) {
        return BeanCopyUtils.copyBeanList(
                lambdaQuery()
                        .like(Article::getArticleContent, keyword)
                        .list()
                , ArticleSearchResp.class);
    }

    @Override
    public void likeArticle(Integer articleId) {
        if (!lambdaQuery().eq(Article::getId, articleId).exists())
            throw new RuntimeException("点赞文章异常:[文章不存在]");
        //文章点赞量+1
        stringRedisTemplate.opsForZSet()
                .incrementScore(SystemConstants.ARTICLE_LIKE_COUNT, String.valueOf(articleId), 1D);
        // 点赞文章,若用户已点赞当前文章则取消点赞
        Integer id = webUtils.getRequestUser().getId();
        if (Boolean.TRUE.equals(stringRedisTemplate.opsForSet()
                .isMember(SystemConstants.USER_ARTICLE_LIKE + id, articleId)))
            stringRedisTemplate.opsForSet()
                    .remove(SystemConstants.USER_ARTICLE_LIKE + id, articleId);
        else
            stringRedisTemplate.opsForSet()
                    .add(SystemConstants.USER_ARTICLE_LIKE + id, String.valueOf(articleId));
    }

    @Override
    @Transactional // 发生异常就回滚
    public void addArticle(ArticleReq articleReq) {
        try {
            //判断分类是否存在
            Integer categoryId =  categoryService.lambdaQuery().select(Category::getId)
                    .eq(Category::getCategoryName, articleReq.getCategoryName()).one().getId();
            if(categoryId == null)
                throw new RuntimeException("文章对应分类不存在");
            //设置文章创建时间、分类ID
            Article newArticle = BeanCopyUtils.copyBean(articleReq, Article.class)
                    .setCreateTime(DateTime.now())
                    .setCategoryId(categoryId);
            //若请求参数中没有文章缩略图，则使用网站默认缩略图
            if (articleReq.getArticleCover() == null || articleReq.getArticleCover().isEmpty())
                newArticle.setArticleCover(siteConfigService.getSiteConfig().getArticleCover());
            //从应用程序上下文中获取用户ID，设置文章用户ID
            newArticle.setUserId(webUtils.getRequestUser().getId());
            //添加文章
            if (!save(newArticle)) throw new RuntimeException("保存时文章异常");

            //添加文章标签 (新增文章ID, 新增文章标签列表)
            addArticleTag(newArticle.getId(), articleReq.getTagNameList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "添加时文章异常");
        }
    }

    @Override
    @Transactional
    public void deleteArticle(List<Integer> articleIdList) {
        try {
            //删除文章对应数据
            if (!removeBatchByIds(articleIdList))
                throw new RuntimeException();
            //清理该文章弃用标签中的无用标签
            articleTagService.cleanArticleTagList(articleIdList);
        } catch (Exception e) {
            throw new RuntimeException("删除文章异常");
        }
    }

    @Override
    public ArticleInfoResp editArticle(Integer articleId) {
        Article article = getById(articleId);
        //获取tag名列表
        List<Integer> tagIdList = articleTagService.lambdaQuery()
                .eq(ArticleTag::getArticleId, articleId)
                .list()
                .stream()
                .map(ArticleTag::getTagId)
                .toList();
        List<String> tagNameList = tagService.listByIds(tagIdList)
                .stream()
                .map(Tag::getTagName)
                .toList();
        //设置文章分类名、标签名列表
        return BeanCopyUtils.copyBean(article, ArticleInfoResp.class)
                .setCategoryName(categoryService.getById(article.getCategoryId()).getCategoryName())
                .setTagNameList(tagNameList);
    }

    @Override
    public PageResult<ArticleBackResp> getBackArticle(ArticleBackReq articleBackReq) {
        //类型 (1原创 2转载 3翻译)
        Integer articleType = articleBackReq.getArticleType();
        //分类id
        Integer categoryId = articleBackReq.getCategoryId();
        //标签id
        Integer tagId = articleBackReq.getTagId();
        //状态 (1公开 2私密 3评论可见)
        Integer status = articleBackReq.getStatus();
        //关键词
        String keyword = articleBackReq.getKeyword();
        //页数
        Integer page = articleBackReq.getPage();
        //条数
        Integer limit = articleBackReq.getLimit();

        //获取符合条件的的文章
        List<Article> articleList = page(new Page<>(page, limit), lambdaQuery()
                .eq(status != null, Article::getStatus, status)
                .eq(categoryId != null, Article::getCategoryId, categoryId)
                .eq(articleType != null, Article::getArticleType, articleType)
                .like(keyword != null, Article::getArticleContent, keyword)
                .in(tagId != null, Article::getId, articleTagService
                        .lambdaQuery()
                        .select(ArticleTag::getArticleId)
                        .eq(ArticleTag::getTagId, tagId).list().stream()
                        .map(ArticleTag::getArticleId)
                        .toList())
                .getWrapper()).getRecords();

        //填充分类名
        Map<Integer, String> nameMap = getCategoryNameMap();
        Map<Integer, String> categoryMap = new HashMap<>();
        articleList.forEach(article -> categoryMap.put(article.getId(), nameMap.get(article.getCategoryId())));
        //填充未定义字段
        List<ArticleBackResp> articleBackRespList = BeanCopyUtils.copyBeanList(articleList, ArticleBackResp.class)
                .stream()
                .peek(articleBackResp -> articleBackResp
                        .setCategoryName(categoryMap.get(articleBackResp.getId()))
                        .setTagOptionList(tagService.getTagOptionList(articleBackResp.getId()))
                        .setLikeCount(Optional.ofNullable(stringRedisTemplate.opsForZSet()
                                        .score(SystemConstants.ARTICLE_LIKE_COUNT, String.valueOf(articleBackResp.getId())))
                                .orElse(0D).longValue())
                        .setViewCount(Optional.ofNullable(stringRedisTemplate.opsForZSet()
                                        .score(SystemConstants.ARTICLE_VIEW_COUNT, articleBackResp.getId()))
                                .orElse(0D).longValue())).toList();
        return new PageResult<>(articleBackRespList.size(), articleBackRespList);
    }

    @Override
    @Transactional
    public void recommendArticle(RecommendReq recommendReq) {
        try {
            if (!lambdaUpdate().eq(Article::getId, recommendReq.getId()).update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("修改文章推荐异常");
        }
    }

    @Override
    @Transactional
    public void recycleArticle(DeleteReq deleteReq) {
        try {
            if (!lambdaUpdate().in(Article::getId, deleteReq.getIdList()).update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("删除文章状态修改异常");
        }
    }

    @Override
    @Transactional
    public void topArticle(TopReq topReq) {
        try {
            if (!lambdaUpdate()
                    .eq(Article::getId, topReq.getId())
                    .set(Article::getIsTop, topReq.getIsTop())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("修改文章置顶异常");
        }
    }

    @Override
    @Transactional
    public void updateArticle(ArticleReq articleReq) {
        try {
            Article newArticle = lambdaQuery().eq(Article::getId, articleReq.getId()).one();
            if (newArticle == null)
                throw new RuntimeException("文章不存在");
            //添加文章标签
            addArticleTag(articleReq.getId(), articleReq.getTagNameList());
            //清理文章标签
            cleanArticleTag(articleReq.getId(), articleReq.getTagNameList());
            //判断分类是否存在
            Integer categoryId =  categoryService.lambdaQuery().select(Category::getId)
                    .eq(Category::getCategoryName, articleReq.getCategoryName()).one().getId();
            if(categoryId == null)
                throw new RuntimeException("文章对应分类不存在");
            //更新文章表
            if (!lambdaUpdate()
                    .eq(Article::getId, articleReq.getId())
                    .update(BeanCopyUtils.copyBean(articleReq, Article.class)
                            .setCategoryId(categoryId)
                            .setUpdateTime(DateTime.now())))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "修改时文章异常");
        }
    }

    //获取当前文章的下一个或上一个
    //flag(true:下一个,false:上一个)
    private ArticlePaginationResp selectOtherArticle(int id, Boolean flag) {
        //判断 redis 中是否存在文章 ID 列表数据
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(SystemConstants.ARTICLE_LIST))) {
            //获取 redis 中的文章 ID 列表数据,转类型为整型列表
            List<Integer> articleIdList = Objects.requireNonNull(stringRedisTemplate.opsForSet()
                            .members(SystemConstants.ARTICLE_LIST))
                    .stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();
            //获取当前文章 ID 下标
            int index = articleIdList.indexOf(id);
            //判断当前下标的下一个或上一个是否越界
            if ((index == -1) || (flag && index + 1 > articleIdList.size() - 1) || (!flag && index - 1 < 0)) return null;
            //获取目标文章 ID
            int articleId = flag ? articleIdList.get(index + 1) : articleIdList.get(index - 1);
            //返回目标文章
            return baseMapper.getArticlePaginationResp(articleId);
        } else {
            return null;
        }
    }

    //添加文章标签列表
    private void addArticleTag(Integer articleId, List<String> tagNameList) {
        List<String> usedTagNameList = tagService.getTagOptionList(articleId)
                .stream().map(TagOptionResp::getTagName).toList();
        //获取未添加标签名列表
        List<String> newTagNameList = tagNameList
                .stream().filter(tagName -> !usedTagNameList.contains(tagName)).toList();
        //保存标签
        articleTagService.addArticleTag(articleId, newTagNameList);
    }

    //清理文章修改后弃用标签数据
    private void cleanArticleTag(Integer articleId, List<String> tagNameList) {
        //查询弃用标签
        List<Integer> abandonTagIdList = tagService.getTagOptionList(articleId)
                .stream().filter(tagOption -> !tagNameList.contains(tagOption.getTagName()))
                .map(TagOptionResp::getId).toList();
        //清理该文章弃用标签中的无用标签
        articleTagService.cleanArticleTag(articleId, abandonTagIdList);
    }

    //获取分类id，分类名map
    private Map<Integer, String> getCategoryNameMap() {
        Map<Integer, String> categoryNameMap = new HashMap<>();
        categoryService.lambdaQuery()
                .select(Category::getId, Category::getCategoryName)
                .list()
                .forEach(category -> categoryNameMap.put(category.getId(), category.getCategoryName()));
        return categoryNameMap;
    }

    //获取文章分页列表
    @Override
    public List<Article> getArticlePageList(
            Integer page, Integer limit, Integer categoryId, Integer tagId) {
        return page(new Page<>(page, limit), lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(categoryId != null, Article::getCategoryId, categoryId)
                        .in(tagId != null, Article::getId, articleTagService.lambdaQuery()
                                .eq(ArticleTag::getTagId, tagId)
                                .list()
                                .stream()
                                .map(ArticleTag::getArticleId)
                                .toList())
                        .getWrapper()).getRecords();
    }

    @Override
    public ArticleDetailResp getArticleDetail(Integer id) {
        return baseMapper.getArticleDetail(id)
                .setLikeCount(Optional.ofNullable(
                                stringRedisTemplate.opsForZSet().score(
                                        SystemConstants.ARTICLE_LIKE_COUNT, String.valueOf(id)))
                        .orElse(0D).longValue())
                .setViewCount(Optional.ofNullable(
                                stringRedisTemplate.opsForZSet().score(
                                        SystemConstants.ARTICLE_VIEW_COUNT, String.valueOf(id)))
                        .orElse(0D).longValue());
    }

    @Override
    public Map<Integer, Long> getArticleLikeMap() {
        return lambdaQuery().select(Article::getId, Article::getLikeCount)
                .list().stream().collect(Collectors.toMap(Article::getId, Article::getLikeCount));
    }

    @Override
    public Map<Integer, Long> getArticleViewMap() {
        return lambdaQuery().select(Article::getId, Article::getViewCount)
                .list().stream().collect(Collectors.toMap(Article::getId, Article::getViewCount));
    }

    @Override
    @Transactional
    public void updateArticleLikeAndViewCount() {
        try {
            Map<Integer, Long> likeCountMap = Objects.requireNonNull(stringRedisTemplate.opsForZSet()
                            .rangeWithScores(SystemConstants.ARTICLE_LIKE_COUNT, 0, -1))
                    .stream().collect(Collectors.toMap(
                            likeCount -> Integer.valueOf(Objects.requireNonNull(likeCount.getValue())),
                            likeCount -> Objects.requireNonNull(likeCount.getScore()).longValue()));

            Map<Integer, Long> viewCountMap = Objects.requireNonNull(stringRedisTemplate.opsForZSet()
                            .rangeWithScores(SystemConstants.ARTICLE_VIEW_COUNT, 0, -1))
                    .stream().collect(Collectors.toMap(
                            viewCount -> Integer.valueOf(Objects.requireNonNull(viewCount.getValue())),
                            viewCount -> Objects.requireNonNull(viewCount.getScore()).longValue()));

            List<Article> articleList = viewCountMap.keySet().stream()
                    .map(id -> new Article(id, likeCountMap.get(id), viewCountMap.get(id)))
                    .toList();

            if (!updateBatchById(articleList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "文章更新异常:更新文章浏览量与点赞量异常");
        }
    }

    @Override
    public PageResult<ArticleHomeResp> getArticleHomeListByCategory(ArticleConditionReq req) {
        pageUtils.setPage(req);
        List<ArticleHomeResp> articleHomeRespList = baseMapper.getArticleHomeListByCategory(req);
        return new PageResult<>(articleHomeRespList.size(), articleHomeRespList);
    }
}

