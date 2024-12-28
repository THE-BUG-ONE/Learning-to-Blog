package com.blog.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendBackReq {
    //当前页
    private Integer page;
    //条数
    private Integer limit;
    //搜索内容
    private String keyword;
}