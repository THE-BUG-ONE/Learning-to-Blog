package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRecommendResp {
    //文章id
    private Integer id;
    //文章标题
    private String articleTitle;
    //缩略图
    private String articleCover;
    //发表时间
    private Date createTime;
}
