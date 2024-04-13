package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Category;
import com.framework.entity.vo.request.CategoryReq;
import com.framework.entity.vo.response.*;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
public interface CategoryService extends IService<Category> {
    List<CategoryResp> getCategoryList();

    ArticleConditionList getCategoryArticleList(Integer categoryId, Integer current,
                                                        Integer size, Integer tagId);

    void addCategory(CategoryReq categoryReq);

    void addCategory(String categoryName);

    void deleteCategory(List<Integer> categoryIdlist);

    PageResult<CategoryBackResp> getBackCategoryList(Integer current, Integer size, String keyword);

    List<CategoryOptionResp> getCategoryOptionList();

    void updateCategory(CategoryReq categoryReq);
}

