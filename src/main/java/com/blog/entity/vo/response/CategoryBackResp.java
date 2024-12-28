package com.blog.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("handler")
public class CategoryBackResp {
    //分类id
    private Integer id;
    //文章数量
    private Integer articleCount;
    //分类名
    private String categoryName;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}
