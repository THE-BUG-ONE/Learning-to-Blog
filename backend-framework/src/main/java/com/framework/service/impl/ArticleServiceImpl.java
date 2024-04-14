package com.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.Article;
import com.framework.entity.dao.ArticleTag;
import com.framework.entity.dao.Category;
import com.framework.entity.dao.Tag;
import com.framework.entity.vo.request.*;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.service.*;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
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
    private ArticleTagService articleTagService;

    @Lazy
    @Resource
    private TagService tagService;

    @Lazy
    @Resource
    private SiteConfigService siteConfigService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageResult<ArticleHomeResp> getArticleHomeList(Integer current, Integer size) {
        /*{
            "count": 0, 文章数
            "recordList": [ 文章列表
              {
                "articleContent": "string",   文章内容1
                "articleCover": "string", 文章缩略图1
                "articleTitle": "string", 文章标题1
                "category": { 分类
                  "categoryName": "string",
                  "id": 0
                },
                "createTime": "2024-03-25T05:39:38.535Z", 创建时间1
                "id": 0, 文章id1
                "isTop": 0, 文章置顶1
                "tagVOList": [    文章标签
                  {
                    "id": 0,
                    "tagName": "string"
                  }
                ]
              }
            ]
          }
        */
        List<Article> articleList = this.getArticlePageList(current, size);

        //存储文章分类列表
        Map<Integer, CategoryOptionResp> articleCategoryVOMap = getArticleCategoryVOMap();

        List<ArticleHomeResp> articleHomeRespList = articleList
                        .stream()
                        .map(article -> BeanCopyUtils.copyBean(article, ArticleHomeResp.class)
                                .setCategory(articleCategoryVOMap.get(article.getCategoryId()))
                                .setTagVOList(tagService.getTagOptionList(article.getId())))
                        .toList();
        return new PageResult<>(articleHomeRespList.size(), articleHomeRespList);
    }

    @Override
    public List<ArticleRecommendResp> getArticleRecommendList() {
        /*[
            {
                "articleCover": "string",
                "articleTitle": "string",
                "createTime": "2024-03-28T04:04:43.419Z",
                "id": 0
            }
        ]*/
        List<Article> articleList = this.getArticleList().stream()
                .filter(article -> article.getIsRecommend() == 1).toList();
        return BeanCopyUtils.copyBeanList(articleList, ArticleRecommendResp.class);
    }

    @Override
    public ArticleResp getArticleDetail(Integer articleId) {
        /*{
        "articleContent": "string",
        "articleCover": "string",
        "articleTitle": "string",
        "articleType": 0,
        "category": {
          "categoryName": "string",
          "id": 0
        },
        "createTime": "2024-03-28T07:00:47.751Z",
        "id": 0,
        "lastArticle": {
          "articleCover": "string",
          "articleTitle": "string",
          "id": 0
        },
            "likeCount": 0,
        "nextArticle": {
          "articleCover": "string",
          "articleTitle": "string",
          "id": 0
        },
        "tagVOList": [
          {
            "id": 0,
            "tagName": "string"
          }
        ],
        "updateTime": "2024-03-28T07:00:47.751Z",
            "viewCount": 0
        }*/
        //浏览量+1
        redisTemplate.opsForZSet().incrementScore(
                SystemConstants.ARTICLE_VIEW_COUNT, articleId, 1D);
        //获取article
        Article article = this.getById(articleId);
        //填充article属性
        return BeanCopyUtils.copyBean(article, ArticleResp.class)
                .setCategory(getArticleCategoryVOMap().get(article.getCategoryId()))
                .setTagVOList(tagService.getTagOptionList(articleId))
                .setNextArticle(selectOtherArticle(articleId, true))
                .setLastArticle(selectOtherArticle(articleId, false))
                .setLikeCount(Optional.ofNullable(
                        redisTemplate.opsForZSet().score(
                                SystemConstants.ARTICLE_LIKE_COUNT, articleId))
                        .orElse(0D).intValue())
                .setViewCount(Optional.ofNullable(
                        redisTemplate.opsForZSet().score(
                                SystemConstants.ARTICLE_VIEW_COUNT, articleId))
                        .orElse(0D).intValue());
    }

    @Override
    public List<ArticleSearchResp> getArticleSearchList(String keyword) {
        /*[
        {
            "articleContent": "string",
            "articleTitle": "string",
            "id": 0,
            "isDelete": 0,
            "status": 0
        }
        ]*/

        return BeanCopyUtils.copyBeanList(
                this.getArticleList().stream()
                        .filter(article -> article.getArticleTitle().contains(keyword))
                        .toList(),
                ArticleSearchResp.class);
    }

    @Override
    public void likeArticle(Integer articleId) {
        redisTemplate.opsForZSet().incrementScore(
                SystemConstants.ARTICLE_LIKE_COUNT, articleId, 1D);
    }

    @Override
    @Transactional // 发生异常就回滚
    public void addArticle(ArticleReq articleReq) {
        try {
            //如果分类不存在添加分类
            this.addArticleCategory(articleReq);
            //封装
            Article newArticle = BeanCopyUtils.copyBean(articleReq, Article.class)
                    .setCreateTime(DateUtil.date())
                    .setCategoryId(
                            categoryService.lambdaQuery()
                                    .select(Category::getId)
                                    .eq(Category::getCategoryName, articleReq.getCategoryName())
                                    .one().getId());
            if (articleReq.getArticleCover() == null)
                newArticle.setArticleCover(
                        siteConfigService.getSiteConfig().getArticleCover());
            //TODO 需要完成获取用户id,暂时固定为1
            newArticle.setUserId(1);
            //添加文章
            if (!this.save(newArticle)) throw new RuntimeException();
            articleReq.setId(this.lambdaQuery()
                    .orderByDesc(Article::getCreateTime)
                    .last(SystemConstants.LAST_LIMIT_1).one()
                    .getId());
            //添加文章标签
            this.addArticleTag(articleReq);
        } catch (Exception e) {
            throw new RuntimeException("添加文章异常");
        }
    }

    @Override
    @Transactional
    public void deleteArticle(List<Integer> articleIdList) {
        articleTagService.removeBatchByIds(articleTagService.lambdaQuery()
                .select(ArticleTag::getId)
                .in(ArticleTag::getArticleId, articleIdList)
                .list().stream().map(ArticleTag::getId).toList());
        this.removeBatchByIds(articleIdList);
    }

    @Override
    public ArticleInfoResp editArticle(Integer articleId) {
        /*{
            "articleContent": "string",
            "articleCover": "string",
            "articleTitle": "string",
            "articleType": 0,
            "categoryName": "string",
            "id": 0,
            "isRecommend": 0,
            "isTop": 0,
            "status": 0,
            "tagNameList": [
                "string"
            ]
        },*/
        Article article = this.getById(articleId);
        if (article == null) return null;
        //获取tag名列表
        List<Integer> tagIdList = articleTagService.lambdaQuery()
                .eq(ArticleTag::getArticleId, articleId).list()
                .stream().map(ArticleTag::getTagId).toList();
        List<String> tagNameList = tagService.listByIds(tagIdList)
                .stream().map(Tag::getTagName).toList();
        //封装
        return BeanCopyUtils.copyBean(article, ArticleInfoResp.class)
                .setCategoryName(
                        categoryService.getById(article.getCategoryId()).getCategoryName())
                .setTagNameList(tagNameList);
    }

    @Override
    public PageResult<ArticleBackResp> getBackArticle(ArticleBackReq articleBackReq) {
        /*{
        "count": 0,
        "recordList": [
          {
            "articleCover": "string",
            "articleTitle": "string",
            "articleType": 0,
            "categoryName": "string",
            "createTime": "2024-04-12T05:35:09.556Z",
            "id": 0,
            "isDelete": 0,
            "isRecommend": 0,
            "isTop": 0,
            "likeCount": 0,
            "status": 0,
            "tagVOList": [
              {
                "id": 0,
                "tagName": "string"
              }
            ],
            "viewCount": 0
          }
        ]}*/

        //类型 (1原创 2转载 3翻译)
        Integer articleType = articleBackReq.getArticleType();
        //分类id
        Integer categoryId = articleBackReq.getCategoryId();
        //标签id
        Integer tagId = articleBackReq.getTagId();
        //是否删除 (0否 1是)
        Integer isDelete = articleBackReq.getIsDelete();
        //状态 (1公开 2私密 3评论可见)
        Integer status = articleBackReq.getStatus();
        //关键词
        String keyword = articleBackReq.getKeyword();
        //页数
        Integer current = articleBackReq.getCurrent();
        //条数
        Integer size = articleBackReq.getSize();

        //获取符合条件的的文章
        List<Article> articleList = this.page(new Page<>(current, size),
                        this.lambdaQuery()
                                .eq(status != null, Article::getStatus, status)
                                .eq(isDelete != null, Article::getIsDelete, isDelete)
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
        articleList.forEach(article ->
                article.setCategoryName(this.getCategoryNameMap().get(article.getCategoryId())));
        //填充未定义字段
        List<ArticleBackResp> articleBackRespList = BeanCopyUtils.copyBeanList(articleList, ArticleBackResp.class)
                        .stream().peek(articleBackResp -> articleBackResp
                                .setTagVOList(tagService.getTagOptionList(articleBackResp.getId()))
                                .setLikeCount(Optional.ofNullable(
                                        redisTemplate.opsForZSet().score(
                                                SystemConstants.ARTICLE_LIKE_COUNT, articleBackResp.getId()))
                                        .orElse(0D).intValue())
                                .setViewCount(Optional.ofNullable(
                                        redisTemplate.opsForZSet().score(
                                                SystemConstants.ARTICLE_VIEW_COUNT, articleBackResp.getId()))
                                        .orElse(0D).intValue())).toList();
        return new PageResult<>(articleBackRespList.size(), articleBackRespList);
    }

    @Override
    @Transactional
    public void recommendArticle(RecommendReq recommendReq) {
        try {
            if (!this.lambdaUpdate()
                    .eq(Article::getId, recommendReq.getId())
                    .set(Article::getIsRecommend, recommendReq.getIsRecommend())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("文章推荐修改异常");
        }
    }

    @Override
    @Transactional
    public void recycleArticle(DeleteReq deleteReq) {
        try {
            if (!this.lambdaUpdate()
                    .in(Article::getId, deleteReq.getIdList())
                    .set(Article::getIsDelete, deleteReq.getIsDelete())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("文章删除状态修改异常");
        }
    }

    @Override
    @Transactional
    public void topArticle(TopReq topReq) {
        try {
            if (!this.lambdaUpdate()
                    .eq(Article::getId, topReq.getId())
                    .set(Article::getIsTop, topReq.getIsTop())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("文章置顶修改异常");
        }
    }

    @Override
    @Transactional
    public void updateArticle(ArticleReq articleReq) {
        try {
            //添加文章标签
            this.addArticleTag(articleReq);
            //若分类不存在添加文章分类
            this.addArticleCategory(articleReq);
            Article article = BeanCopyUtils.copyBean(articleReq, Article.class)
                    .setCategoryId(categoryService.lambdaQuery()
                            .eq(Category::getCategoryName, articleReq.getCategoryName())
                            .one().getId())
                    .setUpdateTime(DateUtil.date());
            if (!this.saveOrUpdate(article))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("文章修改异常");
        }
    }

    //获取当前文章的下一个或上一个
    //flag(true:下一个,false:上一个)
    private ArticlePaginationResp selectOtherArticle(int id, Boolean flag) {
        //将所有有效文章以主键排序
        List<Article> articleList = this.getArticleList()
                .stream()
                .sorted(Comparator.comparing(Article::getId))
                .toList();
        //获取当前文章索引
        int index = articleList.indexOf(this.getById(id));
        //判断列表边界
        if ((index == -1) || (flag && index + 1 > articleList.size() - 1) || (!flag && index - 1 < 0)) return null;
        //获取目标文章
        Article article = articleList.get(flag ? index + 1 : index - 1);
        return article == null ? null :
                BeanCopyUtils.copyBean(article, ArticlePaginationResp.class);
    }

    //获取文章分类列表（分类id，分类名）
    private Map<Integer, CategoryOptionResp> getArticleCategoryVOMap() {
        Map<Integer, CategoryOptionResp> categoryVOMap = new HashMap<>();
        BeanCopyUtils.copyBeanList(categoryService.list(), CategoryOptionResp.class)
                .forEach(articleCategoryVO ->
                        categoryVOMap.put(articleCategoryVO.getId(),articleCategoryVO));
        return categoryVOMap;
    }

    //获取有效文章列表
    private List<Article> getArticleList() {
        return this.lambdaQuery()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                .list();
    }

    //添加文章标签列表
    private void addArticleTag(ArticleReq article) {
        //获取已有标签名列表
        List<String> savedTagNameList = tagService.lambdaQuery()
                .select(Tag::getTagName).list()
                .stream().map(Tag::getTagName).toList();
        //获取未添加标签名列表
        List<String> tagNameList = article.getTagNameList()
                .stream().filter(tagName -> !savedTagNameList.contains(tagName)).toList();
        //保存标签
        tagService.addTag(tagNameList);
        //添加文章标签外键
        articleTagService.addArticleTag(article.getId(), article.getTagNameList());
    }

    //添加文章分类
    private void addArticleCategory(ArticleReq articleReq) {
        Category category = categoryService.lambdaQuery()
                .select(Category::getId)
                .eq(Category::getCategoryName, articleReq.getCategoryName())
                .one();
        if (category == null)
            categoryService.addCategory(articleReq.getCategoryName());
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

    public List<Article> getArticlePageList(Integer current, Integer size) {
        return this.page(new Page<>(current, size),
                this.lambdaQuery()
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC))
                .getRecords();
    }

    //获取文章分页列表
    @Override
    public List<Article> getArticlePageList(
            Integer current, Integer size, Integer categoryId, Integer tagId) {
        return this.page(new Page<>(current, size), this.lambdaQuery()
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
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
}

