package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopReq {
    //id
    private Integer id;
    //是否置顶 (0否 1是)
    private Integer isTop;
}
