package com.framework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.RestBean;
import com.framework.constants.SystemConstants;
import com.framework.entity.Article;
import com.framework.entity.ArticleTag;
import com.framework.entity.Tag;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.service.ArticleService;
import com.framework.service.ArticleTagService;
import com.framework.service.CategoryService;
import com.framework.service.TagService;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    ArticleMapper articleMapper;

    @Lazy
    @Resource
    private CategoryService categoryService;

    @Lazy
    @Resource
    private ArticleTagService articleTagService;

    @Lazy
    @Resource
    private TagService tagService;

    @Override
    public RestBean<ArticleRespVO> getArticleList(Integer current, Integer size) {
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
                this.lambdaQuery()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                .orderByDesc(Article::getIsTop)
                .getWrapper());
        //优化：减少调用数据库
        Map<Integer, ArticleCategoryVO> articleCategoryVOMap = getArticleCategoryVOMap();

        List<ArticleVO> articleVOList =
                p.getRecords()
                        .stream()
                        .map(article -> BeanCopyUtils.copyBean(article,ArticleVO.class)
                                .setArticleCategoryVO(articleCategoryVOMap.get(article.getCategoryId()))
                                .setArticleTagVOList(getArticleTagVOList(article.getId())))
                        .toList();
        ArticleRespVO articleRespVO = new ArticleRespVO(articleVOList.size(), articleVOList);
        return RestBean.success(articleRespVO);
    }

    @Override
    public RestBean<List<ArticleRecommendVO>> getArticleRecommendList() {
        /*[
            {
                "articleCover": "string",
                "articleTitle": "string",
                "createTime": "2024-03-28T04:04:43.419Z",
                "id": 0
            }
        ]*/
        List<Article> articleList = this.lambdaQuery()
                .eq(Article::getIsRecommend, SystemConstants.ARTICLE_IS_RECOMMEND)
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                .list();
        List<ArticleRecommendVO> articleRecommendVOList =
                BeanCopyUtils.copyBeanList(articleList, ArticleRecommendVO.class);
        if (articleRecommendVOList != null)
            return RestBean.success(articleRecommendVOList);
        return RestBean.failure();
    }

    @Override
    public RestBean<ArticleDetailVO> getArticleDetail(Integer articleId) {
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
        Article article = articleMapper.selectById(articleId);
        ArticleDetailVO articleDetailVO =
                BeanCopyUtils.copyBean(article, ArticleDetailVO.class);
        articleDetailVO.setArticleCategoryVO(getArticleCategoryVOMap().get(article.getCategoryId()));
        articleDetailVO.setArticleTagVOList(getArticleTagVOList(articleId));
        articleDetailVO.setNext(selectOtherArticle(articleId, true));
        articleDetailVO.setLast(selectOtherArticle(articleId, false));
        return RestBean.success(articleDetailVO);
    }

    //获取当前文章的下一个或上一个
    private ArticleShortVO selectOtherArticle(int id, Boolean flag) {
        Article article;
        //flag(true:下一个,false:上一个)
        if ((article = articleMapper.selectById(flag ? id+1 : id-1)) != null)
            return BeanCopyUtils.copyBean(article, ArticleShortVO.class);
        else
            return null;
    }

    //获取文章分类列表（分类id，分类名）
    private Map<Integer, ArticleCategoryVO> getArticleCategoryVOMap() {
        Map<Integer, ArticleCategoryVO> articleCategoryVOMap = new HashMap<>();
        BeanCopyUtils.copyBeanList(categoryService.list(), ArticleCategoryVO.class)
                .forEach(articleCategoryVO ->
                        articleCategoryVOMap.put(articleCategoryVO.getId(),articleCategoryVO));
        return articleCategoryVOMap;
    }

    //获取文章标签列表（标签id，标签名）
    private List<ArticleTagVO> getArticleTagVOList(int id) {
        return BeanCopyUtils.copyBeanList(tagService.lambdaQuery()
                .in(Tag::getId,articleTagService.lambdaQuery()
                        .eq(ArticleTag::getArticleId, id)
                        .list()
                        .stream()
                        .map(ArticleTag::getTagId)
                        .collect(Collectors.toSet()))
                .list(), ArticleTagVO.class);
    }

}

