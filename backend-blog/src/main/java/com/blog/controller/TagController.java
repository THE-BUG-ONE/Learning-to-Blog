package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.ArticleConditionReq;
import com.framework.entity.vo.response.ArticleConditionList;
import com.framework.entity.vo.response.TagResp;
import com.framework.service.TagService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @SystemLog(businessName = "查看标签下的文章")
    @GetMapping("/article")
    public Result<ArticleConditionList> getTagArticleList(
            @RequestBody @Valid ArticleConditionReq articleConditionReq) {
        ArticleConditionList res = tagService.getArticleConditionList(articleConditionReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "查看标签列表")
    @GetMapping("list")
    public Result<List<TagResp>> getTagList() {
        List<TagResp> res = tagService.getTagList();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
