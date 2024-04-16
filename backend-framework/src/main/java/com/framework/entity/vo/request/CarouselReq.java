package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselReq {
    //主键
    private Integer id;
    //轮播图地址
    private String imgUrl;
    //是否显示 (0否 1是)
    private Integer status;
    //备注
    private String remark;
}
