package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReq {
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //留言内容
    private String messageContent;
}
