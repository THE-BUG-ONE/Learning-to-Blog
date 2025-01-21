package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageAddReq {

    @NotNull
    //根评论id
    private Integer rootId;
    @NotNull
    //父评论id
    private Integer parentId;
    @NotNull
    //父评论用户id
    private Integer fromUserId;
    @NotNull
    //留言内容
    private String message;
}
