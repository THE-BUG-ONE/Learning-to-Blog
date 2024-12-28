package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOptionResp {

    //用户ID
    private Integer id;
    //用户名
    private String username;
}