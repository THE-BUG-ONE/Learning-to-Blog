package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Comment;
import com.blog.entity.vo.request.CommentBackReq;
import com.blog.entity.vo.response.CommentBackResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-22 14:41:34
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentBackResp> getBackCommentList(@Param("param") CommentBackReq commentBackReq);
}

