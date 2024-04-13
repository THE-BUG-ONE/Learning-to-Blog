package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBackResp {
    //分类id
    private Integer id;
    //文章数量
    private Integer articleCount;
    //分类名
    private String categoryName;
    //创建时间
    private Date createTime;
}
