package com.blog.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (ArticleTag)表实体类
 *
 * @author makejava
 * @since 2024-03-25 15:32:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article_tag")
public class ArticleTag {
    @TableId
    //文章标签id
    private Integer id;
    //文章id
    private Integer articleId;
    //标签id
    private Integer tagId;

    public ArticleTag(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}

