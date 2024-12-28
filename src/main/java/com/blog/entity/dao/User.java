package com.blog.entity.dao;

import java.io.Serializable;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
* 
* @TableName user
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    @ApiModelProperty("用户id")
    private Integer id;
    /**
    * 用户昵称
    */
    @NotBlank(message="[用户昵称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户昵称")
    @Length(max= 50,message="编码长度不能超过50")
    private String nickname;
    /**
    * 用户名
    */
    @NotBlank(message="[用户名]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("用户名")
    @Length(max= 50,message="编码长度不能超过50")
    private String username;
    /**
    * 用户密码
    */
    @NotBlank(message="[用户密码]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("用户密码")
    @Length(max= 100,message="编码长度不能超过100")
    private String password;
    /**
    * 头像
    */
    @NotBlank(message="[头像]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("头像")
    @Length(max= 255,message="编码长度不能超过255")
    private String avatar;
    /**
    * 个人网站
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("个人网站")
    @Length(max= 255,message="编码长度不能超过255")
    private String webSite;
    /**
    * 个人简介
    */
    @Size(max= 100,message="编码长度不能超过100")
    @ApiModelProperty("个人简介")
    @Length(max= 100,message="编码长度不能超过100")
    private String intro;
    /**
    * 邮箱
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("邮箱")
    @Length(max= 50,message="编码长度不能超过50")
    private String email;
    /**
    * 登录ip
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("登录ip")
    @Length(max= 50,message="编码长度不能超过50")
    private String ipAddress;
    /**
    * 登录地址
    */
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("登录地址")
    @Length(max= 50,message="编码长度不能超过50")
    private String ipSource;
    /**
    * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
    */
    @NotNull(message="[登录方式 (1邮箱 2QQ 3Gitee 4Github)]不能为空")
    @ApiModelProperty("登录方式 (1邮箱 2QQ 3Gitee 4Github)")
    private Integer loginType;
    /**
    * 是否禁用 (0否 1是)
    */
    @NotNull(message="[是否禁用 (0否 1是)]不能为空")
    @ApiModelProperty("是否禁用 (0否 1是)")
    private Integer isDisable;
    /**
    * 登录时间
    */
    @ApiModelProperty("登录时间")
    private Date loginTime;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
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
