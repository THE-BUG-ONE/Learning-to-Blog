package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.ArticleReq;
import com.framework.entity.vo.request.DeleteReq;
import com.framework.entity.vo.request.RecommendReq;
import com.framework.entity.vo.request.TopReq;
import com.framework.entity.vo.response.ArticleBackResp;
import com.framework.entity.vo.response.ArticleInfoResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {

    @Resource
    private ArticleService articleService;

    //接口：添加文章
    @PostMapping("/add")
    public Result<?> addArticle(@RequestBody @Validated ArticleReq articleReq) {
        articleService.addArticle(articleReq);
        return Result.success();
    }

    //接口：删除文章
    @DeleteMapping("/delete")
    public Result<?> deleteArticle(@RequestBody @Validated List<Integer> articleIdList) {
        articleService.deleteArticle(articleIdList);
        return Result.success();
    }

    //接口：编辑文章
    @GetMapping("/edit/{articleId}")
    public Result<ArticleInfoResp> editArticle(@PathVariable("articleId") Integer articleId) {
        ArticleInfoResp res = articleService.editArticle(articleId);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：查看后台文章列表
    @GetMapping("/list")
    public Result<PageResult<ArticleBackResp>> getBackArticle(
            @Nullable @RequestParam("articleType") @Validated Integer articleType,
            @Nullable @RequestParam("categoryId") @Validated Integer categoryId,
            @RequestParam("current") @Validated Integer current,
            @Nullable @RequestParam("isDelete") @Validated Integer isDelete,
            @Nullable @RequestParam("keyword") @Validated String keyword,
            @RequestParam("size") @Validated Integer size,
            @Nullable @RequestParam("status") @Validated Integer status,
            @Nullable @RequestParam("tagId") @Validated Integer tagId) {
        PageResult<ArticleBackResp> res = articleService.getBackArticle(
                articleType, categoryId, current, isDelete, keyword, size, status, tagId);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：推荐文章
    @PutMapping("/recommend")
    public Result<?> recommendArticle(@RequestBody @Validated RecommendReq recommendReq) {
        articleService.recommendArticle(recommendReq);
        return Result.success();
    }

    //接口：回收或恢复文章
    @PutMapping("/recycle")
    public Result<?> recycleArticle(@RequestBody @Validated DeleteReq deleteReq) {
        articleService.recycleArticle(deleteReq);
        return Result.success();
    }

    //接口：置顶文章
    @PutMapping("/top")
    public Result<?> topArticle(@RequestBody @Validated TopReq topReq) {
        articleService.topArticle(topReq);
        return Result.success();
    }

    //接口：修改文章
    @PutMapping("/update")
    public Result<?> updateArticle(@RequestBody @Validated ArticleReq articleReq) {
        articleService.updateArticle(articleReq);
        return Result.success();
    }
    //TODO
    //接口：上传文章图片
    @PostMapping("/upload")
    public Result<String> uploadImg(@RequestPart MultipartFile file) {
        String res = file.getOriginalFilename();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //
}
