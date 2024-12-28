package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReq {
    //id
    private Integer id;

    //封面
    private String articleCover;

    //标题
    @NotEmpty
    private String articleTitle;

    //文章内容
    @NotEmpty
    private String articleContent;

    //文章简介
    @NotEmpty
    private String articleDesc;

    //是否置顶 (0否 1是）
    @NotNull
    private Integer isTop;

    //状态 (0发表 1草稿)
    @NotNull
    private Integer status;

    //分类名
    @NotEmpty
    private String categoryName;

    //标签名列表
    @NotNull
    private List<String> tagNameList;
}
