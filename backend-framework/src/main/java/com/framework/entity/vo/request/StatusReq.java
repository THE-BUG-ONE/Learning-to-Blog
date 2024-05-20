package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusReq {
    //id
    @NotNull
    private Integer id;
    //是否显示 (0否 1是)
    @NotNull
    private Integer status;
}
