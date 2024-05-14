package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User implements Serializable {

    @TableId
    //用户id    
    private Integer id;
    //用户昵称
    private String nickname;
    //用户名
    private String username;
    //用户密码
    private String password;
    //头像
    private String avatar;
    //个人网站
    private String webSite;
    //个人简介
    private String intro;
    //邮箱
    private String email;
    //登录ip
    private String ipAddress;
    //登录地址
    private String ipSource;
    //登录方式 (1邮箱 2QQ 3Gitee 4Github)
    private Integer loginType;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //登录时间
    private Date loginTime;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public User(String nickname, String username, String password, String avatar,
                String email, Integer loginType, Integer isDisable, Date createTime) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.loginType = loginType;
        this.isDisable = isDisable;
        this.createTime = createTime;
    }
}

