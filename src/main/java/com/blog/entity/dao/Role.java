package com.blog.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Role)表实体类
 *
 * @author makejava
 * @since 2024-05-19 14:16:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
public class Role {
    @TableId
    //角色id
    private String id;
    //角色名称
    private String roleName;
    //角色描述
    private String roleDesc;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

