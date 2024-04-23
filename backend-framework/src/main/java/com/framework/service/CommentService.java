package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Comment;
import com.framework.entity.vo.request.CommentListReq;
import com.framework.entity.vo.request.CommentReq;
import com.framework.entity.vo.response.CommentResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.RecentCommentResp;
import com.framework.entity.vo.response.ReplyResp;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2024-04-22 14:41:34
 */
public interface CommentService extends IService<Comment> {

    void addComment(CommentReq commentReq);

    PageResult<CommentResp> getCommentList(CommentListReq commentListReq);

    void likeComment(Integer commentId);

    List<ReplyResp> getCommentReply(Integer commentId);

    List<RecentCommentResp> getNewComment();
}

