package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagBackReq {
    //当前页
    @NotNull
    private Integer current;
    //条数
    @NotNull
    private Integer size;
    //搜索内容
    private String keyword;
}
