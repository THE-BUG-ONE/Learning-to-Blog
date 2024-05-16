package com.framework.entity.vo.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReq {
    //文章id
    private Integer id;

    //缩略图
    private String articleCover;

    //文章标题
    @NotEmpty
    private String articleTitle;

    //文章内容
    @NotEmpty
    private String articleContent;

    //类型 (1原创 2转载 3翻译)
    private Integer articleType;

    //是否置顶 (0否 1是）
    @NotNull
    private Integer isTop;

    //是否推荐 (0否 1是)
    @NotNull
    private Integer isRecommend;

    //状态 (1公开 2私密 3评论可见)
    @NotNull
    private Integer status;

    //分类名
    @NotEmpty
    private String categoryName;

    //标签名列表
    private List<String> tagNameList;
}
