package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoResp {

    //用户头像
    private String avatar;
    //用户名
    private String username;
    //权限
    private List<String> roles;
    //个人简介
    private String introduction;
}
