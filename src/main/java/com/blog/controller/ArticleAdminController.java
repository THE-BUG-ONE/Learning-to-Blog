package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.ArticleBackResp;
import com.blog.entity.vo.response.ArticleDetailResp;
import com.blog.entity.vo.response.ArticleInfoResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.service.ArticleService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {

    @Resource
    private ArticleService articleService;

    @SystemLog(businessName = "添加文章")
    @PostMapping("/add")
    public Result<?> addArticle(@RequestBody @Valid ArticleReq articleReq) {
        articleService.addArticle(articleReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除文章")
    @DeleteMapping("/delete")
    public Result<?> deleteArticle(@RequestBody @NotNull List<Integer> articleIdList) {
        articleService.deleteArticle(articleIdList);
        return Result.success();
    }

    @SystemLog(businessName = "编辑文章")
    @GetMapping("/edit/{articleId}")
    public Result<ArticleInfoResp> editArticle(@PathVariable("articleId") @NotNull Integer articleId) {
        ArticleInfoResp res = articleService.editArticle(articleId);
        return Result.result(res);
    }

    @SystemLog(businessName = "查看后台文章列表")
    @GetMapping("/list")
    public Result<PageResult<ArticleBackResp>> getBackArticle(@Valid ArticleBackReq articleBackReq) {
        PageResult<ArticleBackResp> res = articleService.getBackArticle(articleBackReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "推荐文章")
    @PutMapping("/recommend")
    public Result<?> recommendArticle(@RequestBody @Valid RecommendReq recommendReq) {
        articleService.recommendArticle(recommendReq);
        return Result.success();
    }

    @SystemLog(businessName = "回收或恢复文章")
    @PutMapping("/recycle")
    public Result<?> recycleArticle(@RequestBody @Valid DeleteReq deleteReq) {
        articleService.recycleArticle(deleteReq);
        return Result.success();
    }

    @SystemLog(businessName = "置顶文章")
    @PutMapping("/top")
    public Result<?> topArticle(@RequestBody @Valid TopReq topReq) {
        articleService.topArticle(topReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改文章")
    @PutMapping("/update")
    public Result<?> updateArticle(@RequestBody @Valid ArticleReq articleReq) {
        articleService.updateArticle(articleReq);
        return Result.success();
    }
    //TODO
    @SystemLog(businessName = "上传文章图片")
    @PostMapping("/upload")
    public Result<String> uploadImg(@RequestPart("file") @NotNull MultipartFile file) {
        String res = file.getOriginalFilename();
        return Result.result(res);
    }

    @SystemLog(businessName = "查看文章详情")
    @GetMapping("/detail")
    public Result<ArticleDetailResp> getArticleDetail(@NotNull Integer id) {
        ArticleDetailResp res = articleService.getArticleDetail(id);
        return Result.result(res);
    }
}
