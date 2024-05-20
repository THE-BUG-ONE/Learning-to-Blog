package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleBackReq extends PageReq{
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //搜索内容
    private String keyword;
}
