package com.framework.entity.vo.request;

import com.framework.entity.vo.PageCalculate;
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
    private Integer current;
    //条数
    @NotNull
    private Integer size;
    //标签id
    @NotNull
    private Integer tagId;
}
