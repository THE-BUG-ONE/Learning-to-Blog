package com.blog.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
* 
* @TableName album
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album implements Serializable {

    /**
    * 相册id
    */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message="[相册id]不能为空")
    @ApiModelProperty("相册id")
    private Integer id;
    /**
    * 相册名
    */
    @NotBlank(message="[相册名]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("相册名")
    @Length(max= 20,message="编码长度不能超过20")
    private String albumName;
    /**
    * 相册封面
    */
    @NotBlank(message="[相册封面]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("相册封面")
    @Length(max= 255,message="编码长度不能超过255")
    private String albumCover;
    /**
    * 相册描述
    */
    @NotBlank(message="[相册描述]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @ApiModelProperty("相册描述")
    @Length(max= 50,message="编码长度不能超过50")
    private String albumDesc;
    /**
    * 状态 (1公开 2私密)
    */
    @NotNull(message="[状态 (1公开 2私密)]不能为空")
    @ApiModelProperty("状态 (1公开 2私密)")
    private Integer status;
    /**
    * 创建时间
    */
    @NotNull(message="[创建时间]不能为空")
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
