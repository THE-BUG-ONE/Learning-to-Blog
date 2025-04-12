package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.BlogBackInfoResp;
import com.blog.entity.vo.response.BlogInfoResp;
import com.blog.service.BlogInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogInfoController {

    @Resource
    private BlogInfoService blogInfoService;

    @SystemLog(businessName = "获取博客信息")
    @GetMapping("/blog-info")
    public Result<BlogInfoResp> getBlogInfo() {
        BlogInfoResp res = blogInfoService.getBlogInfo();
        return Result.result(res);
    }

    @SystemLog(businessName = "上传访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.success();
    }

    @SystemLog(businessName = "获取后台信息")
    @GetMapping("/blog-info-admin")
    public Result<BlogBackInfoResp> getAdminBlogInfo() {
        BlogBackInfoResp res = blogInfoService.getBlogBackInfo();
        return Result.result(res);
    }
}
