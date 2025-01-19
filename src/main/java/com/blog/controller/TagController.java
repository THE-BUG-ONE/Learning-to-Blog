package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.TagOptionResp;
import com.blog.entity.vo.response.TagResp;
import com.blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;

    @SystemLog(businessName = "获取标签列表")
    @GetMapping("list")
    public Result<List<TagResp>> getTagList() {
        List<TagResp> res = tagService.getTagList();
        return Result.result(res);
    }

    @SystemLog(businessName = "获取标签选项")
    @GetMapping("/option")
    public Result<List<TagOptionResp>> getTagOptionList() {
        List<TagOptionResp> res = tagService.getTagOptionList(null);
        return Result.result(res);
    }
}
