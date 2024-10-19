package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableReq {
    //id
    @NotNull
    private Integer id;
    //是否禁用 (0否 1是)
    @NotNull
    private Integer isDisable;
}
