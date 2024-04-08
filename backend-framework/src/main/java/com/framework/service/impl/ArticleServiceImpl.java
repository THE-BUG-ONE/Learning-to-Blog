package com.framework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.Result;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.Article;
import com.framework.entity.dao.ArticleTag;
import com.framework.entity.dao.Tag;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.service.ArticleService;
import com.framework.service.ArticleTagService;
import com.framework.service.CategoryService;
import com.framework.service.TagService;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final LambdaQueryChainWrapper<Article> articleWrapper = this.lambdaQuery();

    @Override
    public Result<PageResult<ArticleHomeResp>> getArticleHomeList(Integer current, Integer size) {
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
        PageResult<ArticleHomeResp> articleRespVO = new PageResult<>(articleHomeRespList.size(), articleHomeRespList);
        return Result.success(articleRespVO);
    }

    @Override
    public Result<List<ArticleRecommendResp>> getArticleRecommendList() {
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
        List<ArticleRecommendResp> articleRecommendRespList =
                BeanCopyUtils.copyBeanList(articleList, ArticleRecommendResp.class);
        return articleRecommendRespList != null ?
                Result.success(articleRecommendRespList) :
                Result.failure();
    }

    @Override
    public Result<ArticleResp> getArticleDetail(Integer articleId) {
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
        ArticleResp articleResp = BeanCopyUtils.copyBean(article, ArticleResp.class)
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
        return Result.success(articleResp);
    }

    @Override
    public Result<List<ArticleSearchResp>> getArticleSearchList(String keyword) {
        /*[
        {
            "articleContent": "string",
            "articleTitle": "string",
            "id": 0,
            "isDelete": 0,
            "status": 0
        }
        ]*/
        List<ArticleSearchResp> articleSearchRespList =
                BeanCopyUtils.copyBeanList(
                        this.getArticleList().stream()
                                .filter(article -> article.getArticleTitle().contains(keyword))
                                .toList(),
                        ArticleSearchResp.class);

        return articleSearchRespList != null ?
                Result.success(articleSearchRespList) :
                Result.failure();
    }

    @Override
    public Result<?> likeArticle(Integer articleId) {
        redisTemplate.opsForZSet().incrementScore(
                SystemConstants.ARTICLE_LIKE_COUNT, articleId, 1D);
        return Result.success();
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

    private List<Article> getArticleList() {
        return articleWrapper
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                .list();
    }

}

