package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {
    //文章id
    private Integer id;
    //文章分类
    private ArticleCategoryVO articleCategoryVO;
    //下一个文章
    private ArticleShortVO next;
    //上一个文章
    private ArticleShortVO last;
    //缩略图
    private String articleCover;
    //文章标题
    private String articleTitle;
    //文章内容
    private String articleContent;
    //类型 (1原创 2转载 3翻译)
    private Integer articleType;
    //文章标签
    private List<ArticleTagVO> articleTagVOList;
    //发表时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
