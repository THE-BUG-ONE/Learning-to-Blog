package com.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.RestBean;
import com.framework.constants.SystemConstants;
import com.framework.entity.Article;
import com.framework.entity.Category;
import com.framework.entity.vo.CategoryVO;
import com.framework.mapper.CategoryMapper;
import com.framework.service.ArticleService;
import com.framework.service.CategoryService;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    ArticleService articleService;

    @Override
    public RestBean<List<CategoryVO>> getCategoryList() {
        //获取分类名，分类ID，分类文章数
        List<Category> categoryList = this.listByIds(articleService
                        .lambdaQuery()
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                        .list()
                        .stream()
                        .map(Article::getCategoryId)
                        .collect(Collectors.toSet()))
                .stream()
                .filter(category -> category.getStatus() == SystemConstants.CATEGORY_STATUS_NORMAL)
                .toList();
        List<CategoryVO> categoryListVO = BeanCopyUtils.copyBeanList(categoryList, CategoryVO.class)
                .stream()
                .peek(categoryVO -> categoryVO.setArticleCount(getArticleCountByCategoryId(categoryVO.getId())))
                .toList();
        return RestBean.success(categoryListVO);
    }
    //根据分类ID获取文章数
    private int getArticleCountByCategoryId(int categoryId) {
        return Math.toIntExact(articleService.getBaseMapper().selectCount(
                articleService.lambdaQuery()
                        .eq(Article::getCategoryId, categoryId).getWrapper()));
    }
}

