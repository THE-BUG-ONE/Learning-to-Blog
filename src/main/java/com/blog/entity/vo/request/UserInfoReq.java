package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReq {
    //用户id
    @NotNull
    private Integer id;
    //昵称
    private String nickname;
    //用户名
    private String username;
    //个人简介
    private String intro;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //角色id
    private Integer roleId;
}
