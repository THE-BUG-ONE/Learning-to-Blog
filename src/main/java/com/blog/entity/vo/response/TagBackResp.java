package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagBackResp {
    //标签id
    private Integer id;
    //标签名
    private String tagName;
    //文章数量
    private Integer articleCount;
    //创建时间
    private Date createTime;
}
