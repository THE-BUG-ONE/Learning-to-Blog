package com.blog.entity.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(value = "handler")
public class ArticleDetailResp {
    //id
    private Integer id;
    //作者
    private String author;
    //分类
    private String categoryName;
    //缩略图
    private String articleCover;
    //标题
    private String articleTitle;
    //摘要
    private String articleDesc;
    //内容
    private String articleContent;
    //是否置顶 (0否 1是）
    private Integer isTop;
    //状态 (0草稿 1发表)
    private Integer status;
    //标签列表
    private List<TagOptionResp> tagNameList;
    //点赞量
    private Long likeCount;
    //浏览量
    private Long viewCount;
    //发表时间
    private Date createTime;
}
