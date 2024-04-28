package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KeywordReq extends PageCalculate{
    //当前页
    private Integer current;
    //条数
    private Integer size;
    //搜索内容
    private String keyword;
}
