package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleShortVO {
    //文章id
    private Integer id;
    //文章标题
    private String articleTitle;
    //缩略图
    private String articleCover;
}
