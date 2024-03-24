package com.blog.controller;

import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/hot-article-list")
    public String hotArticleList() {
        //查询热门文章，封装成RestBean返回
        return articleService.getHotArticleList();
    }

    @GetMapping("/article-list")
    public String articleList() {
        //查询所有文章
        return articleService.getArticleList();
    }
}
