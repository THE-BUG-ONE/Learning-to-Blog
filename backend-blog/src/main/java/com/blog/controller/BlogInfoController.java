package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.response.BlogBackInfoResp;
import com.framework.entity.vo.response.BlogInfoResp;
import com.framework.service.BlogInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogInfoController {

    @Resource
    BlogInfoService blogInfoService;

    //接口：查看博客信息
    @GetMapping("/")
    public Result<BlogInfoResp> getBlogInfo() {
        return blogInfoService.getBlogInfo();
    }

    //接口：查看关于我信息
    @GetMapping("/about")
    public Result<String> about() {
        return blogInfoService.about();
    }

    //接口：上传访客信息
    @PostMapping("/report")
    public Result<?> report() {
        return blogInfoService.report();
    }

    //接口：查看后台信息
    @GetMapping("/admin")
    public Result<BlogBackInfoResp> admin() {
        BlogBackInfoResp blogBackInfoResp = blogInfoService.getBlogBackInfo();
        return blogBackInfoResp != null ?
                Result.success(blogBackInfoResp) :
                Result.failure();
    }
}