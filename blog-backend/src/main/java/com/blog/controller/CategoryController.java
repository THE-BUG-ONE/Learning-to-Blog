package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.ArticleConditionReq;
import com.blog.entity.vo.response.ArticleConditionList;
import com.blog.entity.vo.response.CategoryResp;
import com.blog.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @SystemLog(businessName = "查看分类列表")
    @GetMapping("/list")
    public Result<List<CategoryResp>> getCategoryList() {
        List<CategoryResp> res = categoryService.getCategoryList();
        return Result.result(res);
    }

    @SystemLog(businessName = "查看分类下的文章")
    @GetMapping("/article")
    public Result<ArticleConditionList> getCategoryArticleList(
            @Valid ArticleConditionReq articleConditionReq) {
        ArticleConditionList res = categoryService.getArticleConditionList(articleConditionReq);
        return Result.result(res);
    }

}
