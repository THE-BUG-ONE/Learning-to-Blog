package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReq {
    //分类名
    @NotEmpty
    private String categoryName;
    //分类id
    private Integer id;

}
