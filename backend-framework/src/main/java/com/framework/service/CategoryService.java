package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Category;
import com.framework.entity.vo.response.ArticleConditionList;
import com.framework.entity.vo.response.CategoryResp;

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
}

