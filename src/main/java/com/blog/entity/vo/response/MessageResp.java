package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResp {
    //留言id
    private Integer id;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //留言内容
    private String messageContent;
}
