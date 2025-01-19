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
    //登录方式
    private Integer loginType;
}
