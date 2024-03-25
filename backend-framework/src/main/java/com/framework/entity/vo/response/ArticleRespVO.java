package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRespVO {
    //文章数
    Integer count;
    //文章列表
    List<ArticleVO> articleVOList;
}
