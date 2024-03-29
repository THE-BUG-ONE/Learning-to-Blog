package com.framework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.RestBean;
import com.framework.constants.SystemConstants;
import com.framework.entity.Article;
import com.framework.entity.ArticleTag;
import com.framework.entity.Category;
import com.framework.entity.Tag;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.mapper.CategoryMapper;
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
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Lazy
    @Resource
    ArticleMapper articleMapper;

    @Lazy
    @Resource
    private ArticleService articleService;

    @Lazy
    @Resource
    private TagService tagService;

    @Lazy
    @Resource
    private ArticleTagService articleTagService;

    private final LambdaQueryChainWrapper<Category> categoryWrapper = this.lambdaQuery();

    @Override
    public RestBean<List<CategoryVO>> getCategoryList() {
        /*[
        {
            "articleCount": 0,
            "categoryName": "string",
            "id": 0
        }
        ]*/
        List<CategoryVO> categoryVOList =
                BeanCopyUtils.copyBeanList(categoryWrapper.list(), CategoryVO.class)
                        .stream()
                        .filter(categoryVO -> {
                            Long count = articleService.lambdaQuery()
                                    .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                                    .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                                    .eq(Article::getCategoryId, categoryVO.getId())
                                    .count();
                            categoryVO.setArticleCount(Math.toIntExact(count));
                            return count > 0;
                        })
                        .toList();

        return RestBean.success(categoryVOList);
    }

    @Override
    public RestBean<CategoryArticleVO> getCategoryArticleList(Integer categoryId,
                                                              Integer current,
                                                              Integer size,
                                                              Integer tagId) {
        /*{
        "articleConditionVOList": [{
            "articleCover": "string",
            "articleTitle": "string",
            "category": {
              "categoryName": "string",
              "id": 0
            },
            "createTime": "2024-03-29T06:40:20.925Z",
            "id": 0,
            "tagVOList": [
              {
                "id": 0,
                "tagName": "string"
              }
             ]
        }],
        "name": "string"
        }*/
        IPage<Article> p =  articleMapper.selectPage(new Page<>(current, size),
                articleService.lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUBLIC)
                        .eq(Article::getIsDelete, SystemConstants.ARTICLE_NOT_DELETE)
                        .eq(Article::getCategoryId, categoryId)
                        .getWrapper());

        List<Article> articleList = p.getRecords()
                .stream()
                .filter(article -> articleTagService.lambdaQuery()
                        .eq(ArticleTag::getTagId, tagId)
                        .exists())
                .toList();
        List<ArticleConditionVO> articleConditionVOList =
                BeanCopyUtils.copyBeanList(articleList, ArticleConditionVO.class)
                        .stream()
                        .peek(articleConditionVO -> {
                            articleConditionVO.setArticleCategoryVO(getArticleCategoryVOMap().get(articleMapper.selectById(articleConditionVO.getId()).getCategoryId()));
                            articleConditionVO.setArticleTagVOList(getArticleTagVOList(articleConditionVO.getId()));
                        })
                        .toList();
        CategoryArticleVO categoryArticleVO = new CategoryArticleVO(
                tagService.getById(tagId).getTagName(),
                articleConditionVOList);

        return RestBean.success(categoryArticleVO);
    }

    //获取文章分类列表（分类id，分类名）
    private Map<Integer, ArticleCategoryVO> getArticleCategoryVOMap() {
        Map<Integer, ArticleCategoryVO> articleCategoryVOMap = new HashMap<>();
        BeanCopyUtils.copyBeanList(this.list(), ArticleCategoryVO.class)
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

