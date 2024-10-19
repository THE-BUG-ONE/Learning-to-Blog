package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReq {
    //用户昵称
    @NotEmpty
    private String nickname;
    //个人简介
    private String intro;
}
