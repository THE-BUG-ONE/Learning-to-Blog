package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuResp {

    private Boolean alwaysShow;

    //子菜单列表
    private List<UserMenuResp> children;

    //菜单组件
    private String component;

    //路由其他信息Response
    private MeteResp mete;

    //菜单名称
    private String name;

    //路由地址
    private String path;

    //重定向地址
    private String redirect;
}
