package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResp {
    //留言id
    private Integer id;
    //留言用户
    private UserResp user;
    //留言内容
    private String message;
    //创建时间
    private Date createTime;
}
