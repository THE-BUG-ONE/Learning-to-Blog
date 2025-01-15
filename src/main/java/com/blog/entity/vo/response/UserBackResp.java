package com.blog.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties("handler")
public class UserBackResp {
    //用户id
    private Integer id;
    //用户昵称
    private String nickname;
    //用户名
    private String username;
    //头像
    private String avatar;
    //简介
    private String intro;
    //邮箱
    private String email;
    //登录ip
    private String ipAddress;
    //登录地址
    private String ipSource;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //登录时间
    private Date loginTime;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //用户角色
    private List<String> roleList;
}
