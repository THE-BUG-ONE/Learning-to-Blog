package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Category)表实体类
 *
 * @author makejava
 * @since 2024-03-24 14:19:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_category")
public class Category {
    @TableId
    //分类id    
    private Integer id;
    //分类名
    private String categoryName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
