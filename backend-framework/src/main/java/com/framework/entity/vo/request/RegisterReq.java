package com.framework.entity.vo.request;

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
}
