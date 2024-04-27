package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Comment;
import com.framework.entity.vo.request.CommentBackReq;
import com.framework.entity.vo.response.CommentBackResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

