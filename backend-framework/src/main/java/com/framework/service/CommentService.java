package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Comment;
import com.framework.entity.vo.request.CommentBackReq;
import com.framework.entity.vo.request.CommentReq;
import com.framework.entity.vo.response.*;

import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2024-04-22 14:41:34
 */
public interface CommentService extends IService<Comment> {

    void addComment(CommentReq commentReq);

    PageResult<CommentResp> getCommentList(CommentBackReq commentBackReq);

    void likeComment(Integer commentId);

    List<ReplyResp> getCommentReply(Integer commentId);

    List<RecentCommentResp> getNewComment();

    void deleteComment(List<Integer> commentIdList);

    PageResult<CommentBackResp> getBackCommentList(CommentBackReq commentBackReq);
}

