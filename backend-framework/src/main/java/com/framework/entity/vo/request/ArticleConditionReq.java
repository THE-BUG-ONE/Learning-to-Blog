package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleConditionReq {
    //分类id
    private Integer categoryId;
    //当前页
    private Integer current;
    //条数
    private Integer size;
    //标签id
    private Integer tagId;
}
