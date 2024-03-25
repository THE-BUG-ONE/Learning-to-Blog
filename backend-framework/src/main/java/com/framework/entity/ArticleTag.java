package com.framework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (ArticleTag)表实体类
 *
 * @author makejava
 * @since 2024-03-25 15:32:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_article_tag")
public class ArticleTag {
    @TableId
    //主键    
    private Integer id;
    //文章id
    private Integer articleId;
    //标签id
    private Integer tagId;
}

