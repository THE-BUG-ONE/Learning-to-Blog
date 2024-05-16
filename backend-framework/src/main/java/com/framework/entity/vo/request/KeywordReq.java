package com.framework.entity.vo.request;

import com.framework.entity.vo.PageCalculate;
import jakarta.validation.constraints.NotEmpty;
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
    private Integer current;
    // 条数
    @NotNull
    private Integer size;
    // 搜索内容
    private String keyword;
}
