package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResp {
    //文章数量
    private Integer articleCount;
    //标签id
    private Integer id;
    //标签名
    private String tagName;
}
