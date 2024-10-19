package com.blog.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBackReq extends PageReq{
    //搜索内容
    private String keyword;
    //登录方式 (1邮箱 2QQ 3Gitee 4Github)
    private Integer loginType;
}
