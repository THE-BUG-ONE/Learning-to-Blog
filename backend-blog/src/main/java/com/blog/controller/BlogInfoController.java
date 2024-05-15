package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
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
    private BlogInfoService blogInfoService;

    @SystemLog(businessName = "查看博客信息")
    @GetMapping("/")
    public Result<BlogInfoResp> getBlogInfo() {
        BlogInfoResp blogInfoResp = blogInfoService.getBlogInfo();
        return blogInfoResp == null ?
                Result.failure() :
                Result.success(blogInfoResp);
    }

    @SystemLog(businessName = "查看关于我信息")
    @GetMapping("/about")
    public Result<String> about() {
        String about = blogInfoService.about();
        return about == null ?
                Result.failure() :
                Result.success(about);
    }

    @SystemLog(businessName = "上传访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }

    @SystemLog(businessName = "查看后台信息")
    @GetMapping("/admin")
    public Result<BlogBackInfoResp> admin() {
        BlogBackInfoResp blogBackInfoResp = blogInfoService.getBlogBackInfo();
        return blogBackInfoResp == null ?
                Result.failure() :
                Result.success(blogBackInfoResp);
    }
}
