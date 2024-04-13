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
import com.framework.entity.vo.request.CategoryReq;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Override
    public List<CategoryResp> getCategoryList() {
        /*[
        {
            "articleCount": 0,
            "categoryName": "string",
            "id": 0
        }
        ]*/
        //封装
        return BeanCopyUtils.copyBeanList(this.lambdaQuery().list(), CategoryResp.class)
                .stream()
                .peek(categoryResp -> categoryResp.setArticleCount(Math.toIntExact(
                        articleService.lambdaQuery()
                                .eq(Article::getCategoryId, categoryResp.getId())
                                .count())))
                .toList();
    }

    @Override
    public ArticleConditionList getCategoryArticleList(Integer categoryId,
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
        //获取当前Tag的所有ArticleId
        List<Integer> articleIdList = articleTagService.lambdaQuery()
                .eq(ArticleTag::getTagId, tagId).list().stream()
                .map(ArticleTag::getArticleId)
                .toList();
        //筛选包含当前Tag的所有Article
        List<Article> articleList = p.getRecords()
                .stream()
                .filter(article -> articleIdList.contains(article.getId()))
                .toList();
        //存储文章分类列表
        Map<Integer, CategoryOptionResp> articleCategoryVOMap = getArticleCategoryVOMap();
        //封装
        List<ArticleConditionResp> articleConditionRespList =
                BeanCopyUtils.copyBeanList(articleList, ArticleConditionResp.class)
                        .stream()
                        .peek(articleConditionResp -> {
                            articleConditionResp.setCategory(articleCategoryVOMap.get(articleMapper.selectById(articleConditionResp.getId()).getCategoryId()));
                            articleConditionResp.setTagVOList(getArticleTagVOList(articleConditionResp.getId()));
                        })
                        .toList();

        return new ArticleConditionList(
                tagService.getById(tagId).getTagName(),
                articleConditionRespList);
    }

    @Override
    @Transactional
    public void addCategory(String categoryName) {
        try {
            //若分类存在取消添加
            if (this.lambdaQuery().eq(Category::getCategoryName, categoryName).exists() ||
                    !this.save(new Category(categoryName, DateUtil.date()))
            ) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("添加分类异常");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(List<Integer> categoryIdlist) {
        try {
            //获取有文章的分类列表
            Set<Integer> idList = articleService.lambdaQuery()
                    .select(Article::getCategoryId)
                    .list()
                    .stream()
                    .map(Article::getCategoryId).collect(Collectors.toSet());
            //删除文章数为0的分类
            if (!this.removeBatchByIds(
                    categoryIdlist.stream().filter(id -> !idList.contains(id)).toList()))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("删除分类异常");
        }
    }

    @Override
    public PageResult<CategoryBackResp> getBackCategoryList(Integer current, Integer size, String keyword) {
        /*{
            "articleCount": 0,
            "categoryName": "string",
            "createTime": "2024-04-13T05:47:43.625Z",
            "id": 0
        }*/
        //若关键词存在匹配关键词
        IPage<Category> p = this.page(new Page<>(current, size),
                this.lambdaQuery()
                        .like(keyword != null, Category::getCategoryName, keyword)
                        .getWrapper());
        List<CategoryBackResp> categoryBackRespList =
                BeanCopyUtils.copyBeanList(p.getRecords(), CategoryBackResp.class)
                        .stream()
                        .peek(categoryBackResp -> categoryBackResp.setArticleCount(
                                Math.toIntExact(articleService.lambdaQuery()
                                        .eq(Article::getCategoryId, categoryBackResp.getId())
                                        .count()))).toList();
        return new PageResult<>(categoryBackRespList.size(), categoryBackRespList);
    }

    @Override
    public List<CategoryOptionResp> getCategoryOptionList() {
        return BeanCopyUtils.copyBeanList(this.list(), CategoryOptionResp.class);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryReq categoryReq) {
        String categoryName = categoryReq.getCategoryName();
        Integer id = categoryReq.getId();
        try {
            if (id == null ||
                    this.lambdaQuery().eq(Category::getCategoryName, categoryName).exists() ||
                    !this.lambdaUpdate()
                            .eq(Category::getId,id)
                            .set(Category::getCategoryName, categoryName)
                            .set(Category::getUpdateTime, DateUtil.date()).update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("分类名重复或分类不存在");
        }
    }

    @Override
    @Transactional
    public void addCategory(CategoryReq categoryReq) {
        String categoryName = categoryReq.getCategoryName();
        Integer id = categoryReq.getId();
        try {
            if (!this.save(id == null ?
                    new Category(categoryName, DateUtil.date()) :
                    new Category(id, categoryName, DateUtil.date())
            )) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("添加分类异常");
        }
    }

    //获取文章分类列表（分类id，分类名）
    private Map<Integer, CategoryOptionResp> getArticleCategoryVOMap() {
        Map<Integer, CategoryOptionResp> articleCategoryVOMap = new HashMap<>();
        BeanCopyUtils.copyBeanList(this.list(), CategoryOptionResp.class)
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
}

