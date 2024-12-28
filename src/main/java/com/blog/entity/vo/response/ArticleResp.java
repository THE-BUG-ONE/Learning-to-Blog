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
@JsonIgnoreProperties("handler")
public class ArticleResp {
    //文章id
    private Integer id;
    //文章分类
    private CategoryOptionResp category;
    //下一个文章
    private ArticlePaginationResp nextArticle;
    //上一个文章
    private ArticlePaginationResp lastArticle	;
    //缩略图
    private String articleCover;
    //文章标题
    private String articleTitle;
    //文章内容
    private String articleContent;
    //类型 (1原创 2转载 3翻译)
    private Integer articleType;
    //点赞量
    private Long likeCount;
    //浏览量
    private Long viewCount;
    //文章标签
    private List<TagOptionResp> tagVOList;
    //发表时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
