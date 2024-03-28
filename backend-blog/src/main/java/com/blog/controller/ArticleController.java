package com.blog.controller;

import com.framework.RestBean;
import com.framework.entity.vo.response.ArticleDetailVO;
import com.framework.entity.vo.response.ArticleRecommendVO;
import com.framework.entity.vo.response.ArticleRespVO;
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
    public RestBean<List<ArticleRecommendVO>> getArticleRecommendList() {
        return articleService.getArticleRecommendList();
    }

    //接口：首页文章列表
    @GetMapping("/list")
    public RestBean<ArticleRespVO> getArticleList(
            @RequestParam("current") @Validated Integer current,
            @RequestParam("size") @Validated Integer size) {
        return articleService.getArticleList(current, size);
    }
    //接口：查看文章详情
    @GetMapping("/{articleId}")
    public RestBean<ArticleDetailVO> getArticle(@PathVariable("articleId") Integer articleId) {
        return articleService.getArticleDetail(articleId);
    }
}
