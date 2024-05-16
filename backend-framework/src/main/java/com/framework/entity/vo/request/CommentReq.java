package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReq {
    //类型 (1文章 2友链 3说说)
    @NotNull
    private Integer commentType;
    //类型id (类型为友链则没有值)
    @NotNull
    private Integer typeId;
    //父评论id
    @NotNull
    private Integer parentId;
    //回复评论id
    @NotNull
    private Integer replyId;
    //评论内容
    @NotNull
    private String commentContent;
    //回复用户id
    @NotNull
    private Integer toUid;
}
