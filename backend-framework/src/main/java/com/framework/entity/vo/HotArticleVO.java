package com.framework.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVO {
    //文章id
    @TableId
    private Integer id;
    //文章标题
    private String articleTitle;
    //是否推荐 (0否 1是)
    private Integer isRecommend;
}
