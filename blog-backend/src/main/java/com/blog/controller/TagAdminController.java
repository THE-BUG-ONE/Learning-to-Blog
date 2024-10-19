package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.TagBackReq;
import com.blog.entity.vo.request.TagReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TagBackResp;
import com.blog.entity.vo.response.TagOptionResp;
import com.blog.service.TagService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class TagAdminController {

    @Resource
    private TagService tagService;

    @SystemLog(businessName = "添加标签")
    @PostMapping("/add")
    public Result<?> addTag(@RequestBody @Valid TagReq tagReq) {
        tagService.addTag(tagReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除标签")
    @DeleteMapping("/delete")
    public Result<?> deleteTag(@RequestBody @NotNull List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看后台标签列表")
    @GetMapping("/list")
    public Result<PageResult<TagBackResp>> getBackTagList(
            @RequestBody @Valid TagBackReq tagBackReq) {
        PageResult<TagBackResp> res = tagService.getBackTagList(tagBackReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "查看标签选项")
    @GetMapping("/option")
    public Result<List<TagOptionResp>> getTagOptionList() {
        List<TagOptionResp> res = tagService.getTagOptionList(null);
        return Result.result(res);
    }

    @SystemLog(businessName = "修改标签")
    @PutMapping("update")
    public Result<?> updateTag(@RequestBody @Valid TagReq tagReq) {
        tagService.updateTag(tagReq);
        return Result.success();
    }
}
