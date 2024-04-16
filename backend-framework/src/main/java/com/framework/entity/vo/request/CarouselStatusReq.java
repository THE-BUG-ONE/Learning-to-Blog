package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselStatusReq {
    //主键
    private Integer id;
    //是否显示 (0否 1是)
    private Integer status;
}
