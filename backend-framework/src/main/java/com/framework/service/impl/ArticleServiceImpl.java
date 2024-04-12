package com.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.Article;
import com.framework.entity.dao.ArticleTag;
import com.framework.entity.dao.Category;
import com.framework.entity.dao.Tag;
import com.framework.entity.vo.request.ArticleReq;
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

    @Resource
    private ArticleMapper articleMapper;

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

    private final LambdaQueryChainWrapper<Article> articleWrapper = this.lambdaQuery();

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
        IPage<Article> p = articleMapper.selectPage(new Page<>(current, size),
                articleWrapper
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                        .orderByDesc(Article::getIsTop)
                        .getWrapper());

        //存储文章分类列表
        Map<Integer, CategoryOptionResp> articleCategoryVOMap = getArticleCategoryVOMap();

        List<ArticleHomeResp> articleHomeRespList = p.getRecords()
                        .stream()
                        .map(article -> BeanCopyUtils.copyBean(article, ArticleHomeResp.class)
                                .setCategory(articleCategoryVOMap.get(article.getCategoryId()))
                                .setTagVOList(getArticleTagVOList(article.getId())))
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
        Article article = articleMapper.selectById(articleId);
        //填充article属性
        return BeanCopyUtils.copyBean(article, ArticleResp.class)
                .setCategory(getArticleCategoryVOMap().get(article.getCategoryId()))
                .setTagVOList(getArticleTagVOList(articleId))
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
    @Transactional(rollbackFor = Exception.class) // 发生异常就回滚
    public void addArticle(ArticleReq article) {
        Article newArticle = BeanCopyUtils.copyBean(article, Article.class);
        newArticle.setCreateTime(DateUtil.date());
        newArticle.setCategoryId(
                categoryService.lambdaQuery()
                        .eq(Category::getCategoryName, article.getCategoryName())
                        .one().getId());
        if (article.getArticleCover() == null)
            newArticle.setArticleCover(
                    siteConfigService.getSiteConfig().getArticleCover());
        //TODO 需要完成获取用户id,暂时固定为1
        newArticle.setUserId(1);
        //添加文章
        this.save(newArticle);
        article.setId(this.lambdaQuery()
                .orderByDesc(Article::getCreateTime)
                .last(SystemConstants.LAST_LIMIT_1).one()
                .getId());
        //添加文章标签
        this.addArticleTag(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(List<Integer> articleIdList) {
        articleTagService.removeByIds(articleTagService.lambdaQuery()
                .select(ArticleTag::getId)
                .in(ArticleTag::getArticleId, articleIdList)
                .list().stream().map(ArticleTag::getId).toList());
        this.removeByIds(articleIdList);
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
    public PageResult<ArticleBackResp> getBackArticle(
            Integer articleType, Integer categoryId, Integer current, Integer isDelete,
            String keyword, Integer size, Integer status, Integer tagId) {
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
        //获取符合条件的的文章
        IPage<Article> p = articleMapper.selectPage(new Page<>(current, size),
                        articleWrapper
                                .eq(status != null, Article::getStatus, status)
                                .eq(isDelete != null, Article::getIsDelete, isDelete)
                                .eq(categoryId != null, Article::getCategoryId, categoryId)
                                .eq(articleType != null, Article::getArticleType, articleType)
                                .like(keyword != null, Article::getArticleContent, keyword)
                                .in(tagId != null, Article::getId,
                                        articleTagService.lambdaQuery().select(ArticleTag::getArticleId)
                                                .eq(ArticleTag::getTagId, tagId).list().stream()
                                                .map(ArticleTag::getArticleId).toList())
                                .getWrapper());
        //填充未定义字段
        List<ArticleBackResp> articleBackRespList =
                BeanCopyUtils.copyBeanList(p.getRecords(), ArticleBackResp.class)
                        .stream().peek(articleBackResp -> articleBackResp
                                .setCategoryName(categoryService.getById(
                                        this.getById(articleBackResp.getId()).getCategoryId()).getCategoryName())
//                                .setTagVOList(this.getArticleTagVOList(articleBackResp.getId()).stream()
//                                        .filter(tag -> tagId == null || articleTagService.lambdaQuery()
//                                                .eq(ArticleTag::getTagId, tagId).exists()).toList())
                                .setTagVOList(this.getArticleTagVOList(articleBackResp.getId()))
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

    //获取当前文章的下一个或上一个
    //flag(true:下一个,false:上一个)
    private ArticlePaginationResp selectOtherArticle(int id, Boolean flag) {
        //将所有有效文章以主键排序
        List<Article> articleList = this.getArticleList().stream().sorted(Comparator.comparing(Article::getId)).toList();
        //获取当前文章索引
        int index = articleList.indexOf(articleMapper.selectById(id));
        //判断列表边界
        if (flag && index + 1 > articleList.size() - 1) return null;
        if (!flag && index - 1 < 0) return null;
        //获取目标文章
        Article article = articleList.get(flag ? index + 1 : index - 1);
        return article == null ? null :
                BeanCopyUtils.copyBean(article, ArticlePaginationResp.class);
    }

    //获取文章分类列表（分类id，分类名）
    private Map<Integer, CategoryOptionResp> getArticleCategoryVOMap() {
        Map<Integer, CategoryOptionResp> articleCategoryVOMap = new HashMap<>();
        BeanCopyUtils.copyBeanList(categoryService.list(), CategoryOptionResp.class)
                .forEach(articleCategoryVO ->
                        articleCategoryVOMap.put(articleCategoryVO.getId(),articleCategoryVO));
        return articleCategoryVOMap;
    }

    //获取文章标签列表（标签id，标签名）
    private List<TagOptionResp> getArticleTagVOList(int id) {
        return BeanCopyUtils.copyBeanList(tagService.lambdaQuery()
                .in(Tag::getId,articleTagService.lambdaQuery()
                        .eq(ArticleTag::getArticleId, id)
                        .list()
                        .stream()
                        .map(ArticleTag::getTagId)
                        .collect(Collectors.toSet()))
                .list(), TagOptionResp.class);
    }

    //获取有效文章列表
    private List<Article> getArticleList() {
        return articleWrapper
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                .list();
    }

    //添加文章标签列表
    private void addArticleTag(ArticleReq article) {
        //获取已有标签名列表
        List<String> savedTagNameList = tagService.lambdaQuery().select(Tag::getTagName).list()
                .stream().map(Tag::getTagName).toList();
        //获取未添加标签名列表
        List<String> tagNameList = article.getTagNameList()
                .stream().filter(tagName -> !savedTagNameList.contains(tagName)).toList();
        //保存标签
        tagService.addTag(tagNameList);
        //添加文章标签外键
        articleTagService.addArticleTag(article.getId(), article.getTagNameList());
    }

}

