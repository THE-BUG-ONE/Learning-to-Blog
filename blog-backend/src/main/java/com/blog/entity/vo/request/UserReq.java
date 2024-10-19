package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    //用户名
    @NotEmpty
    private String username;
    //密码
    @NotEmpty
    private String password;
    //验证码
    @NotEmpty
    private String code;
}
