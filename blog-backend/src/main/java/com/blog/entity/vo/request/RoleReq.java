package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleReq {
    //角色id
    private String id;
    //是否禁用 (0否 1是)
    @NotNull
    private Integer isDisable;
    //菜单id集合
    private List<Integer> menuIdList;
    //角色描述
    private String roleDesc;
    //角色名
    @NotNull
    private String roleName;
}
