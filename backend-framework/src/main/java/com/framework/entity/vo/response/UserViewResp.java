package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewResp {
    //日期
    private Date date;
    //pv
    private Integer pv;
    //uv
    private Integer uv;
}
