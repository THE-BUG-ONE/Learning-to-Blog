package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselBackReq {
    //当前页
    private Integer current;
    //条数
    private Integer size;
    //是否显示 (0否 1是)
    private Integer status;
}
