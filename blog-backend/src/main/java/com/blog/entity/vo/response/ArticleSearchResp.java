package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchResp {
    //文章id
    private Integer id;
    //文章标题
    private String articleTitle;
    //文章内容
    private String articleContent;
    //是否删除 (0否 1是)
    private Integer isDelete;
    //状态 (1公开 2私密 3评论可见)
    private Integer status;
}
