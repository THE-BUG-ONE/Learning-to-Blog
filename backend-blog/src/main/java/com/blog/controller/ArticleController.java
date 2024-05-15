package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.PageReq;
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

    @SystemLog(businessName = "推荐文章")
    @GetMapping("/recommend")
    public Result<List<ArticleRecommendResp>> getRecommendArticleList() {
        List<ArticleRecommendResp> res =  articleService.getArticleRecommendList();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "首页文章列表")
    @GetMapping("/list")
    public Result<PageResult<ArticleHomeResp>> getArticleHomeList(@Validated PageReq pageReq) {
        PageResult<ArticleHomeResp> res = articleService.getArticleHomeList(pageReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "查看文章详情")
    @GetMapping("/{articleId}")
    public Result<ArticleResp> getArticle(@PathVariable("articleId") Integer articleId) {
        ArticleResp res = articleService.getArticleDetail(articleId);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "搜索文章")
    @GetMapping("/search")
    public Result<List<ArticleSearchResp>> getSearchArticle(
            @RequestParam("keyword") @Validated String keyword) {
        List<ArticleSearchResp> res = articleService.getArticleSearchList(keyword);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "点赞文章")
    @PostMapping("/{articleId}/like")
    public Result<?> like(@PathVariable("articleId") Integer articleId) {
        articleService.likeArticle(articleId);
        return Result.success();
    }
}
