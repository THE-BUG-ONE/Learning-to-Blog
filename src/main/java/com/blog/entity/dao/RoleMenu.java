package com.blog.entity.dao;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
* 
* @TableName role_menu
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    @ApiModelProperty("主键")
    private Integer id;
    /**
    * 角色id
    */
    @NotBlank(message="[角色id]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("角色id")
    @Length(max= 20,message="编码长度不能超过20")
    private String roleId;
    /**
    * 菜单id
    */
    @NotNull(message="[菜单id]不能为空")
    @ApiModelProperty("菜单id")
    private Integer menuId;
}
