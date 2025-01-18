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
public class MessageResp {
    //留言id
    private Integer id;
    //父评论id
    private Integer parentId;
    //根评论id
    private Integer rootId;
    //留言用户
    private UserResp user;
    //回复评论用户
    private UserOptionResp fromUser;
    //留言内容
    private String message;
    //创建时间
    private Date createTime;
}
