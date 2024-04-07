package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.response.*;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    //接口：推荐文章
    @GetMapping("/recommend")
    public Result<List<ArticleRecommendResp>> getRecommendArticleList() {
        return articleService.getArticleRecommendList();
    }

    //接口：首页文章列表
    @GetMapping("/list")
    public Result<PageResult<ArticleHomeResp>> getArticleList(
            @RequestParam("current") @Validated Integer current,
            @RequestParam("size") @Validated Integer size) {
        return articleService.getArticleList(current, size);
    }

    //接口：查看文章详情
    @GetMapping("/{articleId}")
    public Result<ArticleResp> getArticle(@PathVariable("articleId") Integer articleId) {
        return articleService.getArticleDetail(articleId);
    }

    //接口：搜索文章
    @GetMapping("/search")
    public Result<List<ArticleSearchResp>> getSearchArticle(
            @RequestParam("keyword") @Validated String keyword) {
        return articleService.getArticleSearchList(keyword);
    }

    //接口：点赞文章
    @PostMapping("/{articleId}/like")
    public Result<?> like(@PathVariable("articleId") Integer articleId) {
        return articleService.likeArticle(articleId);
    }
}
