package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentBackResp {
    //文章标题
    private String articleTitle;
    //头像
    private String avatar;
    //评论内容
    private String commentContent;
    //评论类型
    private Integer commentType;
    //发表时间
    private Date createTime;
    //评论用户昵称
    private String fromNickname;
    //评论id
    private String id;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //被回复用户昵称
    private String toNickname;
}
