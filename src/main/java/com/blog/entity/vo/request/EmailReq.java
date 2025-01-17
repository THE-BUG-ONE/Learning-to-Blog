package com.blog.entity.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailReq {
    //邮箱
    @NotEmpty
    @Email
    private String username;
    //验证码
    @NotEmpty
    private String code;
}
