package com.blog.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (Comment)表实体类
 *
 * @author makejava
 * @since 2024-04-22 14:41:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("comment")
public class Comment {

    @TableId(value = "id", type = IdType.AUTO)
    //评论id
    private Integer id;
    //类型 (1文章 2友链 3说说)
    private Integer commentType;
    //类型id (类型为友链则没有值)
    private Integer typeId;
    //父评论id
    private Integer parentId;
    //回复评论id
    private Integer replyId;
    //评论内容
    private String commentContent;
    //评论用户id
    private Integer fromUid;
    //评论目标用户id
    private Integer toUid;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //评论时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

