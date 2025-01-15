package com.blog.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties("handler")
public class ArticleHomeResp {
    //文章id
    private Integer id;
    //缩略图
    private String articleCover;
    //文章标题
    private String articleTitle;
    //文章简介
    private String articleDesc;
    //文章分类
    private String categoryName;
    //是否置顶 (0否 1是）
    private Integer isTop;
    //发表时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //文章标签
    private List<TagOptionResp> tagNameList;
}
