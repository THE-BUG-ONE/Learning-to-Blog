package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.CommentBackReq;
import com.framework.entity.vo.request.CommentReq;
import com.framework.entity.vo.response.CommentResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RecentCommentResp;
import com.framework.entity.vo.response.ReplyResp;
import com.framework.service.CommentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @SystemLog(businessName = "添加评论")
    @PostMapping("/comment/add")
    public Result<?> addComment(@RequestBody @Valid CommentReq commentReq) {
        commentService.addComment(commentReq);
        return Result.success();
    }

    @SystemLog(businessName = "查看评论")
    @GetMapping("/comment/list")
    public Result<PageResult<CommentResp>> getCommentList(@Valid CommentBackReq commentBackReq) {
        PageResult<CommentResp> res = commentService.getCommentList(commentBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "点赞评论")
    @PostMapping("/comment/{commentId}/like")
    public Result<?> likeComment(@PathVariable("commentId") @NotNull Integer commentId) {
        commentService.likeComment(commentId);
        return Result.success();
    }

    @SystemLog(businessName = "查看回复评论")
    @GetMapping("/comment/{commentId}/reply")
    public Result<List<ReplyResp>> getCommentReply(@PathVariable("commentId") @NotNull Integer commentId) {
        List<ReplyResp> res = commentService.getCommentReply(commentId);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "查看最新评论")
    @GetMapping("/recent/comment")
    public Result<List<RecentCommentResp>> getNewComment() {
        List<RecentCommentResp> res = commentService.getNewComment();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
