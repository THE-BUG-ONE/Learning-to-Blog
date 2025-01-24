package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    //验证码
    @NotNull
    private String code;
    //密码
    @NotNull
    private String password;
    //用户名
    @NotNull
    private String username;
    //昵称
    @NotNull
    private String nickname;
    //邮箱
    @NotNull
    private String email;
}
