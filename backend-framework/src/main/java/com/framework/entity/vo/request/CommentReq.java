package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReq {
    //类型 (1文章 2友链 3说说)
    private Integer commentType;
    //类型id (类型为友链则没有值)
    private Integer typeId;
    //父评论id
    private Integer parentId;
    //回复评论id
    private Integer replyId;
    //评论内容
    private String commentContent;
    //回复用户id
    private Integer toUid;
}
