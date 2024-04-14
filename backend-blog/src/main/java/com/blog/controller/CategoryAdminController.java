package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.CategoryReq;
import com.framework.entity.vo.response.CategoryBackResp;
import com.framework.entity.vo.response.CategoryOptionResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryAdminController {

    @Resource
    private CategoryService categoryService;

    //接口：添加分类
    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Validated CategoryReq categoryReq) {
        categoryService.addCategory(categoryReq);
        return Result.success();
    }

    //接口：删除分类
    @DeleteMapping("/delete")
    public Result<?> deleteCategory(@RequestBody @Validated List<Integer> categoryIdlist) {
        categoryService.deleteCategory(categoryIdlist);
        return Result.success();
    }

    //接口：查看后台分类列表
    @GetMapping("/list")
    public Result<PageResult<CategoryBackResp>> getBackCategoryList(
            @RequestParam("current") @Validated Integer current,
            @RequestParam("size") @Validated Integer size,
            @RequestParam("keyword") @Validated @Nullable String keyword) {
        PageResult<CategoryBackResp> res = categoryService.getBackCategoryList(current, size, keyword);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：查看分类选项
    @GetMapping("/option")
    public Result<List<CategoryOptionResp>> getCategoryOptionList() {
        List<CategoryOptionResp> res = categoryService.getCategoryOptionList();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：修改分类
    @PutMapping("/update")
    public Result<?> updateCategory(@RequestBody @Validated CategoryReq categoryReq) {
        categoryService.updateCategory(categoryReq);
        return Result.success();
    }
}
