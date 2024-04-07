package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResp {
    //分类id
    private Integer id;
    //分类名
    private String categoryName;
    //分类文章数
    private Integer articleCount;
}
