package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselResp {
    //主键
    private Integer id;
    //轮播图标题
    private String title;
    //轮播图信息
    private String remark;
    //轮播图链接
    private String link;
    //轮播图图片地址
    private String imgUrl;
}
