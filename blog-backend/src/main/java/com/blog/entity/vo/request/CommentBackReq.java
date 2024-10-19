package com.blog.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentBackReq {
    //条数
    @NotNull
    private Integer size;
    //当前页
    @NotNull
    private Integer current;
    //评论主题类型
    private Integer commentType;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //搜索内容
    private String keyword;
    //typeId
    private Integer typeId;
}
