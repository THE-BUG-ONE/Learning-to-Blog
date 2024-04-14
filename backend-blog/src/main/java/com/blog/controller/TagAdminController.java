package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.TagBackReq;
import com.framework.entity.vo.request.TagReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.TagBackResp;
import com.framework.entity.vo.response.TagOptionResp;
import com.framework.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class TagAdminController {

    @Resource
    private TagService tagService;

    //接口：添加标签
    @PostMapping("/add")
    public Result<?> addTag(@RequestBody @Validated TagReq tagReq) {
        tagService.addTag(tagReq);
        return Result.success();
    }

    //接口：删除标签
    @DeleteMapping("/delete")
    public Result<?> deleteTag(@RequestBody @Validated List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.success();
    }

    //接口：查看后台标签列表
    @GetMapping("/list")
    public Result<PageResult<TagBackResp>> getBackTagList(
            @RequestBody @Validated TagBackReq tagBackReq) {
        PageResult<TagBackResp> res = tagService.getBackTagList(tagBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：查看标签选项
    @GetMapping("/option")
    public Result<List<TagOptionResp>> getTagOptionList() {
        List<TagOptionResp> res = tagService.getTagOptionList(null);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：修改标签
    @PutMapping("update")
    public Result<?> updateTag(@RequestBody @Validated TagReq tagReq) {
        tagService.updateTag(tagReq);
        return Result.success();
    }
}
