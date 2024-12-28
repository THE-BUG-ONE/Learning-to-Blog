package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserViewResp {
    //日期
    private Date date;
    //pv 浏览次数
    private Integer pv;
    //uv 客户端数
    private Integer uv;
}
