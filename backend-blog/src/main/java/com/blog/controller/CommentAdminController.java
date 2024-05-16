package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.CheckReq;
import com.framework.entity.vo.request.CommentBackReq;
import com.framework.entity.vo.response.CommentBackResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.CommentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Resource
    private CommentService commentService;

    @SystemLog(businessName = "删除评论")
    @DeleteMapping("/delete")
    public Result<?> deleteComment(@RequestBody @NotNull List<Integer> commentIdList ) {
        commentService.deleteComment(commentIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看后台评论")
    @GetMapping("/list")
    public Result<PageResult<CommentBackResp>> getBackCommentList(@Valid CommentBackReq commentBackReq) {
        PageResult<CommentBackResp> res = commentService.getBackCommentList(commentBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "审核评论")
    @GetMapping("/pass")
    public Result<?> getBackCommentList(@Valid CheckReq checkReq) {
        commentService.passComment(checkReq);
        return Result.success();
    }
}
