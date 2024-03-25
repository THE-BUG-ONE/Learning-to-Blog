package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.RestBean;
import com.framework.entity.Category;
import com.framework.entity.vo.response.CategoryVO;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
public interface CategoryService extends IService<Category> {
    RestBean<List<CategoryVO>> getCategoryList();
}

