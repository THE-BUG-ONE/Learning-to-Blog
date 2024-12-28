package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopReq {
    //id
    @NotNull
    private Integer id;
    //是否置顶 (0否 1是)
    @NotNull
    private Integer isTop;
}
