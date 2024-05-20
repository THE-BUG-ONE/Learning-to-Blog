package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResp {
    //角色id
    private String id;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //角色描述
    private String roleDesc;
    //角色名
    private String roleName;
    //创建时间
    private Date createTime;
}
