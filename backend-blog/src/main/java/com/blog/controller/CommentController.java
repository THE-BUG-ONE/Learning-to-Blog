package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.CommentListReq;
import com.framework.entity.vo.request.CommentReq;
import com.framework.entity.vo.response.CommentResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RecentCommentResp;
import com.framework.entity.vo.response.ReplyResp;
import com.framework.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/comment", "/admin/comment", "/recent"})
public class CommentController {

    @Resource
    private CommentService commentService;

    //接口：添加评论
    @PostMapping("/add")
    public Result<?> addComment(@RequestBody @Validated CommentReq commentReq) {
        commentService.addComment(commentReq);
        return Result.success();
    }

    //接口：查看评论
    @GetMapping("/list")
    public Result<PageResult<CommentResp>> getCommentList(@Validated CommentListReq commentListReq) {
        PageResult<CommentResp> res = commentService.getCommentList(commentListReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：点赞评论
    @PostMapping("/{commentId}/like")
    public Result<?> likeComment(@RequestParam("commentId") @Validated Integer commentId) {
        commentService.likeComment(commentId);
        return Result.success();
    }

    //接口：查看回复评论
    @GetMapping("/{commentId}/reply")
    public Result<List<ReplyResp>> getCommentReply(@RequestParam("commentId") @Validated Integer commentId) {
        List<ReplyResp> res = commentService.getCommentReply(commentId);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：查看最新评论
    @GetMapping("/comment")
    public Result<List<RecentCommentResp>> getNewComment() {
        List<RecentCommentResp> res = commentService.getNewComment();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
