package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Category;
import com.blog.entity.vo.request.ArticleConditionReq;
import com.blog.entity.vo.request.CategoryReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.*;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
public interface CategoryService extends IService<Category> {
    List<CategoryResp> getCategoryList();

    ArticleConditionList getArticleConditionList(ArticleConditionReq articleConditionReq);

    CategoryBackResp addCategory(String categoryName);

    void deleteCategory(List<Integer> categoryIdList);

    PageResult<CategoryBackResp> getBackCategoryList(PageReq pageReq);

    List<CategoryOptionResp> getCategoryOptionList();

    void updateCategory(CategoryReq categoryReq);

}

