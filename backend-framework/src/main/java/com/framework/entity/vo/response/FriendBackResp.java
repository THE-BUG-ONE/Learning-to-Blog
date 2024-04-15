package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendBackResp {
    //友链id
    private Integer id;
    //友链名称
    private String name;
    //友链颜色
    private String color;
    //友链头像
    private String avatar;
    //友链地址
    private String url;
    //友链介绍
    private String introduction;
    //创建时间
    private Date createTime;
}
