package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Talk)表实体类
 *
 * @author makejava
 * @since 2024-05-23 15:01:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("talk")
public class Talk {
    @TableId
    //说说id    
    private Integer id;
    //用户id
    private Integer userId;
    //说说内容
    private String talkContent;
    //说说图片
    private String images;
    //是否置顶 (0否 1是)
    private Integer isTop;
    //状态 (0公开 1私密)
    private Integer status;
    //发表时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

