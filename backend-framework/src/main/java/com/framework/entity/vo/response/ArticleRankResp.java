package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRankResp {
    //标题
    private String articleTitle;
    //浏览量
    private Integer viewCount;
}
