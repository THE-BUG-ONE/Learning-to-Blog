package com.framework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * (Article)表实体类
 *
 * @author makejava
 * @since 2024-03-22 16:18:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_article")
public class Article {
    //文章id
    @TableId
    private Integer id;
    //作者id
    private Integer userId;
    //分类id
    private Integer categoryId;
    //文章标题
    private String articleTitle;
    //文章内容
    private String articleContent;
    //类型 (1原创 2转载 3翻译)
    private Integer articleType;
    //是否置顶 (0否 1是）
    private Integer isTop;
    //是否推荐 (0否 1是)
    private Integer isRecommend;
    //状态 (1公开 2草稿 3已删除)
    private Integer status;
    //发表时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

