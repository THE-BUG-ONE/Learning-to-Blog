package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.ArticleReq;
import com.framework.entity.vo.response.ArticleInfoResp;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //接口：删除文章
    @DeleteMapping("/delete")
    public Result<?> deleteArticle(@RequestBody @Validated List<Integer> articleIdList) {
        articleService.deleteArticle(articleIdList);
        return Result.success();
    }

    //接口：编辑文章
    @GetMapping("/edit/{articleId}")
    public Result<ArticleInfoResp> editArticle(@PathVariable("articleId") Integer articleId) {
        ArticleInfoResp articleInfoResp = articleService.editArticle(articleId);
        return articleInfoResp != null ?
                Result.success(articleInfoResp) :
                Result.failure();
    }
}
