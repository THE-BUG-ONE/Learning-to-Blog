package com.blog.entity.vo.request;

import com.blog.entity.vo.PageCalculate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleConditionReq extends PageCalculate {
    //分类id
    @NotNull
    private Integer categoryId;
    //当前页
    @NotNull
    private Integer page;
    //条数
    @NotNull
    private Integer limit;
}
