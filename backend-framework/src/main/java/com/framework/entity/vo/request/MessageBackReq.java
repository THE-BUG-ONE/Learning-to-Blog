package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBackReq {
    //关键词
    private String keyword;
    //页数
    private Integer current;
    //条数
    private Integer size;
    //是否通过 (0否 1是)
    private Integer isCheck;
}
