package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Article;
import com.blog.entity.dao.Category;
import com.blog.entity.vo.request.ArticleConditionReq;
import com.blog.entity.vo.request.CategoryReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.*;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTagService;
import com.blog.service.CategoryService;
import com.blog.service.TagService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private ArticleService articleService;

    @Lazy
    @Resource
    private ArticleMapper articleMapper;

    @Lazy
    @Resource
    private TagService tagService;

    @Lazy
    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private PageUtils pageUtils;

    @Override
    public List<CategoryResp> getCategoryList() {
        return baseMapper.getCategoryList();
    }

    //标签id可为空，为空时从文章字段中获取
    @Override
    public ArticleConditionList getArticleConditionList(ArticleConditionReq req) {
        //设置分页sql中的起始点
        pageUtils.setPage(req);
        //筛选包含当前Tag的所有Article
        List<ArticleConditionResp> res = articleMapper.getArticleConditionList(req);

        //分类名为条件名
        return new ArticleConditionList(
                getById(req.getCategoryId()).getCategoryName(),
                res);
    }

    @Override
    @Transactional
    public CategoryBackResp addCategory(String categoryName) {
        try {
            //若分类存在取消添加
            if (lambdaQuery().eq(Category::getCategoryName, categoryName).exists() ||
                    !save(new Category(categoryName, DateTime.now())))
                throw new RuntimeException();
            return BeanCopyUtils.copyBean(
                    lambdaQuery().eq(Category::getCategoryName, categoryName).one(),
                    CategoryBackResp.class)
                    .setArticleCount(0);
        } catch (Exception e) {
            throw new RuntimeException("添加分类异常:[名称重复,未知异常]");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(List<Integer> categoryIdList) {
        try {
            //获取有文章的分类列表
            Set<Integer> idList = articleService.lambdaQuery()
                    .select(Article::getCategoryId)
                    .list()
                    .stream()
                    .map(Article::getCategoryId).collect(Collectors.toSet());
            //删除文章数为0的分类
            if (!removeBatchByIds(
                    categoryIdList.stream().filter(id -> !idList.contains(id)).toList()))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("删除分类异常:[未知异常]");
        }
    }

    @Override
    public PageResult<CategoryBackResp> getBackCategoryList(PageReq req) {
        pageUtils.setPage(req);
        List<CategoryBackResp> backCategoryList = baseMapper.getBackCategoryList(req);
        return new PageResult<>(backCategoryList.size(), backCategoryList);
    }

    @Override
    public List<CategoryOptionResp> getCategoryOptionList() {
        return BeanCopyUtils.copyBeanList(list(), CategoryOptionResp.class);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryReq categoryReq) {
        try {
            String categoryName = categoryReq.getCategoryName();
            Integer id = categoryReq.getId();
            //判断分类是否已存在
            if (id == null || lambdaQuery()
                    .eq(Category::getCategoryName, categoryName)
                    .exists() || !lambdaUpdate()
                    .eq(Category::getId, id)
                    .set(Category::getCategoryName, categoryName)
                    .set(Category::getUpdateTime, DateTime.now()).update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("修改分类异常:[分类名重复,分类不存在,未知异常]");
        }
    }
}

