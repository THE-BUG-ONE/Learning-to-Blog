package com.blog.controller;

import com.framework.RestBean;
import com.framework.entity.vo.CategoryVO;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public RestBean<List<CategoryVO>> getCategoryList() {
        return categoryService.getCategoryList();
    }
}
