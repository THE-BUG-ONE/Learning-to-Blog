package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBackReq {
    //类型 (1原创 2转载 3翻译)
    private Integer articleType;
    //分类id
    private Integer categoryId;
    //标签id
    private Integer tagId;
    //是否删除 (0否 1是)
    private Integer isDelete;
    //状态 (1公开 2私密 3评论可见)
    private Integer status;
    //关键词
    private String keyword;
    //页数
    @NotNull
    private Integer current;
    //条数
    @NotNull
    private Integer size;

}
