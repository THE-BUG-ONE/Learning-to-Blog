package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReq {
    //昵称
    @NotNull
    private String nickname;
    //头像
    @NotNull
    private String avatar;
    //留言内容
    @NotNull
    private String messageContent;
}
