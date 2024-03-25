package com.blog.controller;

import com.framework.RestBean;
import com.framework.entity.vo.response.ArticleRespVO;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

//    @GetMapping("/hot-article-list")
//    public RestBean<List<HotArticleVO>> hotArticleList() {
//        //查询热门文章，封装成RestBean返回
//        return ;
//    }
    //首页文章列表接口
    @GetMapping("/list")
    public RestBean<ArticleRespVO> articleList(
            @RequestParam("current") @Validated int current,
            @RequestParam("size") @Validated int size) {
        return articleService.getArticleList(current, size);
    }
}
