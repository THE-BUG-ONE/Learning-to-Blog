package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.response.ArticleConditionList;
import com.framework.entity.vo.response.CategoryResp;
import com.framework.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    //接口：查看分类列表
    @GetMapping("/list")
    public Result<List<CategoryResp>> getCategoryList() {
        return categoryService.getCategoryList();
    }

    //接口：查看分类下的文章
    @GetMapping("/article")
    public Result<ArticleConditionList> getCategoryArticleList(
            @RequestParam("categoryId") @Validated Integer categoryId,
            @RequestParam("current") @Validated Integer current,
            @RequestParam("size") @Validated Integer size,
            @RequestParam("tagId") @Validated Integer tagId
    ) {
        return categoryService.getCategoryArticleList(categoryId, current, size, tagId);
    }

}
