package com.framework.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.dao.Article;
import com.framework.entity.dao.Category;
import com.framework.entity.vo.request.ArticleConditionReq;
import com.framework.entity.vo.request.KeywordReq;
import com.framework.entity.vo.request.CategoryReq;
import com.framework.entity.vo.response.*;
import com.framework.mapper.ArticleMapper;
import com.framework.mapper.CategoryMapper;
import com.framework.service.ArticleService;
import com.framework.service.ArticleTagService;
import com.framework.service.CategoryService;
import com.framework.service.TagService;
import com.framework.utils.BeanCopyUtils;
import com.framework.utils.PageUtils;
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
        pageUtils.setCurrent(req);
        //筛选包含当前Tag的所有Article
        List<ArticleConditionResp> res = articleMapper.getArticleConditionList(req);

        //分类名为条件名
        return new ArticleConditionList(
                this.getById(req.getCategoryId()).getCategoryName(),
                res);
    }

    @Override
    @Transactional
    public void addCategory(String categoryName) {
        try {
            //若分类存在取消添加
            if (this.lambdaQuery().eq(Category::getCategoryName, categoryName).exists() ||
                    !this.save(new Category(categoryName, DateTime.now()))
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
    public PageResult<CategoryBackResp> getBackCategoryList(KeywordReq req) {
        pageUtils.setCurrent(req);
        List<CategoryBackResp> backCategoryList = baseMapper.getBackCategoryList(req);
        return new PageResult<>(backCategoryList.size(), backCategoryList);
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
                            .set(Category::getUpdateTime, DateTime.now()).update())
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
                    new Category(id, categoryName, DateTime.now())
            )) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("添加分类异常");
        }
    }

    //获取文章分类列表（分类id，分类名）
    @Override
    public CategoryOptionResp getCategoryOptionVO(Integer categoryId) {
        return baseMapper.getCategoryOption(categoryId);
    }
}

