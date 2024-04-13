package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReq {
    //分类名
    private String categoryName;
    //分类id
    private Integer id;

    public CategoryReq(String categoryName) {
        this.categoryName = categoryName;
    }
}
