package com.blog.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselBackReq {
    //当前页
    private Integer page;
    //条数
    private Integer limit;
    //是否显示 (0否 1是)
    private Integer status;
}
