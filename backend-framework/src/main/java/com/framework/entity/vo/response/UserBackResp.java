package com.framework.entity.vo.response;

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
public class UserBackResp {
    //用户id
    private Integer id;
    //用户昵称
    private String nickname;
    //头像
    private String avatar;
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
    //用户角色
    private List<UserRoleResp> roleList;
}
