package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoResp {
    //文章数量
    private Long articleCount;
    //分类数量
    private Long categoryCount;
    //标签数量
    private Long tagCount;
    //网站访问量
    private Long viewCount;
}
