package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Comment;
import com.blog.entity.dao.User;
import com.blog.entity.vo.request.CheckReq;
import com.blog.entity.vo.request.CommentBackReq;
import com.blog.entity.vo.request.CommentReq;
import com.blog.entity.vo.response.*;
import com.blog.mapper.CommentMapper;
import com.blog.service.CommentService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-04-22 14:41:34
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Lazy
    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private WebUtils webUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public void addComment(CommentReq commentReq) {
        try {
            //获取评论用户id
            Integer userId = webUtils.getRequestUser().getId();

            Comment comment = BeanCopyUtils.copyBean(commentReq, Comment.class)
                    .setFromUid(userId)
                    .setIsCheck(0)
                    .setCreateTime(DateTime.now());
            if (!save(comment))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("添加评论异常:[未知异常]");
        }
    }

    @Override
    public PageResult<CommentResp> getCommentList(CommentBackReq commentBackReq) {
        Integer size = commentBackReq.getSize();
        Integer current = commentBackReq.getCurrent();
        Integer commentType = commentBackReq.getCommentType();
        Integer isCheck = commentBackReq.getIsCheck();
        String keyword = commentBackReq.getKeyword();
        Integer typeId = commentBackReq.getTypeId();
        //获取筛选后的评论列表
        List<Comment> commentList = page(new Page<>(current, size),
                        lambdaQuery()
                                .eq(commentType != null, Comment::getCommentType, commentType)
                                .eq(isCheck != null, Comment::getIsCheck, isCheck)
                                .eq(isCheck == null, Comment::getIsCheck, SystemConstants.COMMENT_IS_CHECKED)
                                .eq(typeId != null, Comment::getTypeId, typeId)
                                .like(keyword != null, Comment::getCommentContent, keyword)
                                .getWrapper())
                .getRecords();

        //获取用户id与对应的用户
        Map<Integer, User> userMap = getUserMap();

        List<CommentResp> commentRespList = BeanCopyUtils.copyBeanList(commentList, CommentResp.class)
                .stream()
                .peek(commentResp ->{
                    User user = userMap.get(commentResp.getFromUid());
                    List<ReplyResp> replyVOList = getReplyVOList(commentResp.getFromUid(), commentResp.getId());
                    commentResp
                            .setAvatar(user.getAvatar())
                            .setFromNickname(user.getNickname())
                            .setLikeCount(Integer.valueOf(Optional.ofNullable(
                                    stringRedisTemplate.opsForValue().get(
                                            SystemConstants.COMMENT_LIKE_COUNT + commentResp.getId()))
                                    .orElse("0")))
                            .setReplyCount(replyVOList.size())
                            .setReplyVOList(replyVOList);
                }).toList();
        return new PageResult<>(commentRespList.size(), commentRespList);
    }

    @Override
    public void likeComment(Integer commentId) {
        stringRedisTemplate.opsForValue()
                .increment(SystemConstants.COMMENT_LIKE_COUNT + commentId);
    }

    @Override
    public List<ReplyResp> getCommentReply(Integer commentId) {
        Comment comment = getById(commentId);
        if (comment == null) return null;
        return getReplyVOList(comment.getFromUid(), commentId);
    }

    @Override
    public List<RecentCommentResp> getNewComment() {
        //获取最新评论列表
        List<Comment> commentList = lambdaQuery()
                .eq(Comment::getIsCheck, SystemConstants.COMMENT_IS_CHECKED)
                .last(SystemConstants.COMMENT_NEW_NUM)
                .list();
        //获取用户id对应的用户
        Map<Integer, User> userMap = getUserMap();
        //获取评论id对应的用户id
        Map<Integer, Integer> idMap = commentList.stream().collect(
                Collectors.toMap(Comment::getId, Comment::getFromUid));
        //封装
        return BeanCopyUtils.copyBeanList(commentList, RecentCommentResp.class)
                .stream()
                .peek(comment -> {
                    User user = userMap.get(idMap.get(comment.getId()));
                    comment
                            .setAvatar(user.getAvatar())
                            .setNickname(user.getNickname());
                }).toList();
    }

    @Override
    @Transactional
    public void deleteComment(List<Integer> commentIdList) {
        try {
            //Set结构防重
            Set<Integer> idList = new HashSet<>();
            //获取对应评论的回复评论
            commentIdList.forEach(id -> idList.addAll(lambdaQuery()
                    .select(Comment::getId)
                    .eq(Comment::getReplyId, id)
                    .list()
                    .stream()
                    .map(Comment::getId)
                    .toList()));
            //集合所有应操作的评论
            idList.addAll(commentIdList);
            if (!removeBatchByIds(idList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("删除评论异常:[未知异常]");
        }
    }

    @Override
    public PageResult<CommentBackResp> getBackCommentList(CommentBackReq commentBackReq) {
        List<CommentBackResp> respList = commentMapper.getBackCommentList(commentBackReq);
        return new PageResult<>(respList.size(), respList);
    }

    @Override
    @Transactional
    public void passComment(CheckReq checkReq) {
        try {
            if (!lambdaUpdate()
                    .in(Comment::getId, checkReq.getIdList())
                    .set(Comment::getIsCheck, checkReq.getIsCheck())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("审核评论异常:[未知异常]");
        }
    }

    //获取评论对应的回复评论列表
    private List<ReplyResp> getReplyVOList(int userId, int id) {
        //获取用户id与对应的用户
        Map<Integer, User> userMap = getUserMap();
        //获取对应评论的回复评论
        List<Comment> commentList = lambdaQuery()
                .eq(Comment::getReplyId, id).list();
        //获取对应评论的用户id
        User toUser = userMap.get(userId);
        //封装并返回回复评论列表
        return BeanCopyUtils.copyBeanList(commentList, ReplyResp.class)
                .stream().peek(replyResp -> {
                    User user = userMap.get(replyResp.getFromUid());
                    replyResp
                            .setAvatar(user.getAvatar())
                            .setFromNickname(user.getNickname())
                            .setLikeCount(Integer.valueOf(Optional.ofNullable(
                                    stringRedisTemplate.opsForValue().get(
                                            SystemConstants.COMMENT_LIKE_COUNT + id))
                                    .orElse("0")))
                            .setToNickname(toUser.getNickname())
                            .setToUid(toUser.getId());
                }).toList();
    }

    //获取评论对应的用户
    private Map<Integer, User> getUserMap() {
        Map<Integer, User> userMap = new HashMap<>();
        userService.list().forEach(user -> userMap.put(user.getId(), user));
        return userMap;
    }

}

