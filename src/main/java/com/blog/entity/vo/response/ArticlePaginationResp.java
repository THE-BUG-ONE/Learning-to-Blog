package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePaginationResp {
    //文章id
    private Integer id;
    //文章标题
    private String articleTitle;
}
