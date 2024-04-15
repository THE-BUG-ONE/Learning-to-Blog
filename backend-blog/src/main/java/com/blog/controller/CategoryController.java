package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.ArticleConditionReq;
import com.framework.entity.vo.response.ArticleConditionList;
import com.framework.entity.vo.response.CategoryResp;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //接口：查看分类列表
    @GetMapping("/list")
    public Result<List<CategoryResp>> getCategoryList() {
        List<CategoryResp> res = categoryService.getCategoryList();
        return res == null ?
                Result.failure() :
                Result.success(res);
    }

    //接口：查看分类下的文章
    @GetMapping("/article")
    public Result<ArticleConditionList> getCategoryArticleList(
            @Validated ArticleConditionReq articleConditionReq) {
        ArticleConditionList res = categoryService.getArticleConditionList(articleConditionReq);
        return res == null ?
                Result.failure() :
                Result.success(res);
    }

}
