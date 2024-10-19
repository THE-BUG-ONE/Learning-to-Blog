package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleConditionList {
    //条件名
    private String name;
    //文章列表
    private List<ArticleConditionResp> articleConditionVOList;
}
