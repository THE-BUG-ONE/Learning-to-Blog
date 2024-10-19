package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselBackResp {
    //主键
    private Integer id;
    //轮播图地址
    private String imgUrl;
    //是否显示 (0否 1是)
    private Integer status;
    //备注
    private String remark;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
