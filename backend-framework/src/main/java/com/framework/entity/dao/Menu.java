package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Menu)表实体类
 *
 * @author makejava
 * @since 2024-05-21 13:58:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_menu")
public class Menu {
    @TableId
    //主键    
    private Integer id;
    //父菜单id (paren_id为0且type为M则是一级菜单)
    private Integer parentId;
    //权限类型 (M目录 C菜单 B按钮)
    private String menuType;
    //名称
    private String menuName;
    //路由地址
    private String path;
    //菜单图标
    private String icon;
    //菜单组件
    private String component;
    //权限标识
    private String perms;
    //是否隐藏 (0否 1是)
    private Integer isHidden;
    //是否禁用 (0否 1是)
    private Integer isDisable;
    //排序
    private Integer orderNum;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

