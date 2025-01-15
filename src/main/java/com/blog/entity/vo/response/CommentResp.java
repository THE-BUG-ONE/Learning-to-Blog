package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentResp {
    //头像
    private String avatar;
    //评论内容
    private String commentContent;
    //用户id
    private UserResp user;
    //评论时间
    private String createTime;
    //昵称
    private String fromNickname;
    //评论用户id
    private Integer fromUid;
    //评论id
    private Integer id;
    //点赞数
    private Long likeCount;
    //回复量
    private Integer replyCount;
    //回复列表
    private List<ReplyResp> replyVOList;
}
