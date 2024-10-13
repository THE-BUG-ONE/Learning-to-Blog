package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors
@NoArgsConstructor
public class UserRoleReq {
    //用户id
    @NotNull
    private Integer id;
    //昵称
    @NotNull
    private String nickname;
    //角色id
    @NotNull
    private List<Integer> roleIdList;
}
