package com.framework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.RestBean;
import com.framework.constants.SystemConstants;
import com.framework.entity.Article;
import com.framework.entity.ArticleTag;
import com.framework.entity.Category;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.service.ArticleService;
import com.framework.service.ArticleTagService;
import com.framework.service.CategoryService;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public RestBean<ArticleRespVO> getArticleList(int current, int size) {
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
        IPage<Article> p = this.getBaseMapper().selectPage(new Page<>(current, size),
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
                                .setArticleTagVOList(getArticleTagVOList(article.getId()))
                        )
                        .toList();
        ArticleRespVO articleRespVO = new ArticleRespVO(articleVOList.size(), articleVOList);
        return RestBean.success(articleRespVO);
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
        List<ArticleTag> articleTagList = articleTagService.lambdaQuery()
                .eq(ArticleTag::getArticleId, id).list();
        return BeanCopyUtils.copyBeanList(articleTagList, ArticleTagVO.class);
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
        return RestBean.success(articleRecommendVOList);
    }
}

