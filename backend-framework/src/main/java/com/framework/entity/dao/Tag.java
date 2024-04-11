package com.framework.entity.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2024-03-25 13:44:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tag")
public class Tag {
    @TableId
    //标签id    
    private Integer id;
    //标签名
    private String tagName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public Tag(String tagName, DateTime date) {
        this.tagName = tagName;
        this.createTime = date;
    }
}

