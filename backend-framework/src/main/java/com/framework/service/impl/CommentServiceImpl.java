package com.framework.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import com.framework.entity.vo.request.CommentBackReq;
import com.framework.entity.vo.request.CommentReq;
import com.framework.entity.vo.response.*;
import com.framework.mapper.CommentMapper;
import com.framework.entity.dao.Comment;
import com.framework.service.CommentService;
import com.framework.service.UserService;
import com.framework.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public void addComment(CommentReq commentReq) {
        try {
            //获取评论用户id
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication();
            Integer userId = loginUser.getUser().getId();

            Comment comment = BeanCopyUtils.copyBean(commentReq, Comment.class)
                    .setFromUid(userId)
                    .setIsCheck(0)
                    .setCreateTime(DateTime.now());
            if (!this.save(comment))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("评论添加异常");
        }
    }

    @Override
    public PageResult<CommentResp> getCommentList(CommentBackReq commentBackReq) {
        /*{
            "count": 0,
            "recordList": [
              {
                "avatar": "string",
                "commentContent": "string",
                "createTime": "2024-04-22T06:50:47.297Z",
                "fromNickname": "string",
                "fromUid": 0,
                "id": 0,
                "likeCount": 0,
                "replyCount": 0,
                "replyVOList": [
                  {
                    "avatar": "string",
                    "commentContent": "string",
                    "createTime": "2024-04-22T06:50:47.297Z",
                    "fromNickname": "string",
                    "fromUid": 0,
                    "id": 0,
                    "likeCount": 0,
                    "parentId": 0,
                    "toNickname": "string",
                    "toUid": 0,
                    "webSite": "string"
                  }
                ],
                "webSite": "string"
              }
            ]
          }*/
        Integer size = commentBackReq.getSize();
        Integer current = commentBackReq.getCurrent();
        Integer commentType = commentBackReq.getCommentType();
        Integer isCheck = commentBackReq.getIsCheck();
        String keyword = commentBackReq.getKeyword();
        Integer typeId = commentBackReq.getTypeId();
        //获取筛选后的评论列表
        List<Comment> commentList = this.page(new Page<>(current, size),
                        this.lambdaQuery()
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
                            .setReplyVOList(replyVOList)
                            .setWebSite(user.getWebSite());
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
        Comment comment = this.getById(commentId);
        if (comment == null) return null;
        return getReplyVOList(comment.getFromUid(), commentId);
    }

    @Override
    public List<RecentCommentResp> getNewComment() {
        //获取最新评论列表
        List<Comment> commentList = this.lambdaQuery()
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
            commentIdList.forEach(id -> idList.addAll(this.lambdaQuery()
                    .select(Comment::getId)
                    .eq(Comment::getReplyId, id)
                    .list()
                    .stream()
                    .map(Comment::getId)
                    .toList()));
            //集合所有应操作的评论
            idList.addAll(commentIdList);
            if (!this.removeBatchByIds(idList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("评论删除异常");
        }
    }

    @Override
    public PageResult<CommentBackResp> getBackCommentList(CommentBackReq commentBackReq) {
        List<CommentBackResp> respList = commentMapper.getBackCommentList(commentBackReq);
        return new PageResult<>(respList.size(), respList);
    }

    //获取评论对应的回复评论列表
    private List<ReplyResp> getReplyVOList(int userId, int id) {
        //获取用户id与对应的用户
        Map<Integer, User> userMap = getUserMap();
        //获取对应评论的回复评论
        List<Comment> commentList = this.lambdaQuery()
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
                            .setToUid(toUser.getId())
                            .setWebSite(toUser.getWebSite());
                }).toList();
    }

    //获取评论对应的用户
    private Map<Integer, User> getUserMap() {
        Map<Integer, User> userMap = new HashMap<>();
        userService.list().forEach(user -> userMap.put(user.getId(), user));
        return userMap;
    }

}

