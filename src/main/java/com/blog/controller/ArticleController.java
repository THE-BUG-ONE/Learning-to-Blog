package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.ArticleConditionReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.*;
import com.blog.service.ArticleService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @SystemLog(businessName = "获取推荐文章")
    @GetMapping("/recommend")
    public Result<List<ArticleRecommendResp>> getRecommendArticleList() {
        List<ArticleRecommendResp> res =  articleService.getArticleRecommendList();
        return Result.result(res);
    }

    @SystemLog(businessName = "获取首页文章列表")
    @GetMapping("/list")
    public Result<PageResult<ArticleHomeResp>> getArticleHomeList(@Valid PageReq pageReq) {
        PageResult<ArticleHomeResp> res = articleService.getArticleHomeList(pageReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "获取文章详情")
    @GetMapping("/{articleId}")
    public Result<ArticleResp> getArticle(@PathVariable("articleId") @NotNull Integer articleId) {
        ArticleResp res = articleService.getArticle(articleId);
        return Result.result(res);
    }

    @SystemLog(businessName = "搜索文章")
    @GetMapping("/search")
    public Result<List<ArticleSearchResp>> getSearchArticle(
            @RequestParam("keyword") String keyword) {
        List<ArticleSearchResp> res = articleService.getArticleSearchList(keyword);
        return Result.result(res);
    }

    @SystemLog(businessName = "文章点赞")
    @PostMapping("/{articleId}/like")
    public Result<?> like(@PathVariable("articleId") @NotNull Integer articleId) {
        articleService.likeArticle(articleId);
        return Result.success();
    }

    @SystemLog(businessName = "获取首页的分类下的文章列表")
    @GetMapping("/category-list")
    public Result<PageResult<ArticleHomeResp>> getArticleHomeListByCategory(@Valid ArticleConditionReq req) {
        PageResult<ArticleHomeResp> res = articleService.getArticleHomeListByCategory(req);
        return Result.result(res);
    }
}
