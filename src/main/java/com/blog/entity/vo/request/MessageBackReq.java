package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBackReq {
    //关键词
    private String keyword;
    //页数
    @NotNull
    private Integer page;
    //条数
    @NotNull
    private Integer limit;
    //是否通过 (0否 1是)
    private Integer isCheck;
}
