package com.blog.entity.vo.request;

import com.blog.entity.vo.PageCalculate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KeywordReq extends PageCalculate {
    // 当前页
    @NotNull
    private Integer page;
    // 条数
    @NotNull
    private Integer limit;
    // 搜索内容
    private String keyword;
}
