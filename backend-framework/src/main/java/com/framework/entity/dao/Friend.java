package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (Friend)表实体类
 *
 * @author makejava
 * @since 2024-04-15 13:50:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_friend")
public class Friend {
    @TableId
    //友链id    
    private Integer id;
    //友链名称
    private String name;
    //友链颜色
    private String color;
    //友链头像
    private String avatar;
    //友链地址
    private String url;
    //友链介绍
    private String introduction;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

