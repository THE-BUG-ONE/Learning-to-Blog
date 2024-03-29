package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleConditionVO {
    //文章id
    private Integer id;
    //缩略图
    private String articleCover;
    //文章标题
    private String articleTitle;
    //文章分类
    private ArticleCategoryVO articleCategoryVO;
    //发表时间
    private Date createTime;
    //文章标签
    private List<ArticleTagVO> articleTagVOList;
}
