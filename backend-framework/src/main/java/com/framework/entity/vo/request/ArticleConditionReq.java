package com.framework.entity.vo.request;

import com.framework.entity.vo.PageCalculate;
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
    private Integer categoryId;
    //当前页
    private Integer current;
    //条数
    private Integer size;
    //标签id
    private Integer tagId;
}
