package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBackResp {
    //留言id
    private Integer id;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //留言内容
    private String messageContent;
    //用户ip
    private String ipAddress;
    //用户地址
    private String ipSource;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //留言时间
    private Date createTime;
}
