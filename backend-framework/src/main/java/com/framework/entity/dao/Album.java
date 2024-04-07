package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Album)表实体类
 *
 * @author makejava
 * @since 2024-03-24 14:12:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_album")
public class Album {
    @TableId
    //相册id    
    private Integer id;
    //相册名
    private String albumName;
    //相册封面
    private String albumCover;
    //相册描述
    private String albumDesc;
    //状态 (1公开 2私密)
    private Integer status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

