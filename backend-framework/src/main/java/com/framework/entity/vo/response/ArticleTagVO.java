package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagVO {
    //标签id
    private Integer id;
    //标签名
    private String tagName;
}
