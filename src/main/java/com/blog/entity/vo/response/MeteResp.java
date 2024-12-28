package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteResp {

    //是否隐藏
    private Boolean hidden;

    //菜单图标
    private String icon;

    //菜单名称
    private String title;
}
