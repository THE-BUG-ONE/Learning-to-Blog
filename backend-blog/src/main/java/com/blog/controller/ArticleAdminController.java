package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.ArticleReq;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {

    @Resource
    private ArticleService articleService;

    //接口：添加文章
    @PostMapping("/add")
    public Result<?> addArticle(@RequestBody @Validated ArticleReq article) {
        articleService.addArticle(article);
        return Result.success();
    }
}
