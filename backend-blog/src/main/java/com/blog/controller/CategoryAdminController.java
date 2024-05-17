package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.CategoryReq;
import com.framework.entity.vo.request.KeywordReq;
import com.framework.entity.vo.response.CategoryBackResp;
import com.framework.entity.vo.response.CategoryOptionResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryAdminController {

    @Resource
    private CategoryService categoryService;

    @SystemLog(businessName = "添加分类")
    @PostMapping("/add")
    public Result<?> addCategory(@RequestBody @Valid CategoryReq categoryReq) {
        categoryService.addCategory(categoryReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除分类")
    @DeleteMapping("/delete")
    public Result<?> deleteCategory(@RequestBody @NotNull List<Integer> categoryIdlist) {
        categoryService.deleteCategory(categoryIdlist);
        return Result.success();
    }

    @SystemLog(businessName = "查看后台分类列表")
    @GetMapping("/list")
    public Result<PageResult<CategoryBackResp>> getBackCategoryList(@Valid KeywordReq keywordReq) {
        PageResult<CategoryBackResp> res = categoryService.getBackCategoryList(keywordReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "查看分类选项")
    @GetMapping("/option")
    public Result<List<CategoryOptionResp>> getCategoryOptionList() {
        List<CategoryOptionResp> res = categoryService.getCategoryOptionList();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "修改分类")
    @PutMapping("/update")
    public Result<?> updateCategory(@RequestBody @Valid CategoryReq categoryReq) {
        categoryService.updateCategory(categoryReq);
        return Result.success();
    }
}
