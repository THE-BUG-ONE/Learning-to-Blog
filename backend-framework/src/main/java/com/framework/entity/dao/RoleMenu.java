package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (RoleMenu)表实体类
 *
 * @author makejava
 * @since 2024-05-20 14:55:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role_menu")
public class RoleMenu {
    @TableId
    //主键    
    private Integer id;
    //角色id
    private String roleId;
    //菜单id
    private Integer menuId;
}

