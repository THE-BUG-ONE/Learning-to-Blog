package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (UserRole)表实体类
 *
 * @author makejava
 * @since 2024-05-19 14:15:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_role")
public class UserRole {
    @TableId
    //主键    
    private Integer id;
    //用户id
    private Integer userId;
    //角色id
    private String roleId;

    public UserRole(Integer userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}

