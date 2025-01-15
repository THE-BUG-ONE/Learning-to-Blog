package com.blog.entity.dao;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("menu")
public class Menu{

    /**
    * 主键
    */

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message="[主键]不能为空")
    private Integer id;
    
    /**
    * 父菜单id (paren_id为0且type为M则是一级菜单)
    */
    @NotNull(message="[父菜单id (paren_id为0且type为M则是一级菜单)]不能为空")
    private Integer parentId;
    
    /**
    * 权限类型 (M目录 C菜单 B按钮)
    */
    @NotNull(message="[权限类型 (M目录 C菜单 B按钮)]不能为空")
    private String menuType;
    
    /**
    * 名称
    */
    @NotBlank(message="[名称]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")private String menuName;
    
    /**
    * 路由地址
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String path;
    
    /**
    * 菜单图标
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")private String icon;
    
    /**
    * 菜单组件
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")private String component;
    
    /**
    * 权限标识
    */
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")private String perms;
    
    /**
    * 是否隐藏 (0否 1是)
    */
    @NotNull(message="[是否隐藏 (0否 1是)]不能为空")
    private Integer isHidden;
    
    /**
    * 是否禁用 (0否 1是)
    */
    @NotNull(message="[是否禁用 (0否 1是)]不能为空")
    private Integer isDisable;
    
    /**
    * 排序
    */
    @NotNull(message="[排序]不能为空")
    private Integer orderNum;
    
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    private Date createTime;
    
    /**
    * 更新时间
    */
    private Date updateTime;
    
}
