package com.blog.controller;

import com.framework.RestBean;
import com.framework.entity.vo.response.ArticleRecommendVO;
import com.framework.entity.vo.response.ArticleRespVO;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
    //推荐文章接口
    @GetMapping("/recommend")
    public RestBean<List<ArticleRecommendVO>> getArticleRecommendList() {
        return articleService.getArticleRecommendList();
    }
    //首页文章列表接口
    @GetMapping("/list")
    public RestBean<ArticleRespVO> getArticleList(
            @RequestParam("current") @Validated Integer current,
            @RequestParam("size") @Validated Integer size) {
        return articleService.getArticleList(current, size);
    }
}
