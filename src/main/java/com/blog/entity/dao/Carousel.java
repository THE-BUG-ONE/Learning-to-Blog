package com.blog.entity.dao;


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
@TableName("carousel")
public class Carousel{

    /**
    * 主键
    */
    @TableId
    @NotNull(message="[主键]不能为空")
    private Integer id;
    
    /**
    * lun'bo'tu'di
    */
    private Integer title;
    
    /**
    * 轮播图地址
    */
    @NotBlank(message="[轮播图地址]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String imgUrl;
    
    /**
    * 是否显示 (0否 1是)
    */
    @NotNull(message="[是否显示 (0否 1是)]不能为空")
    private Integer status;
    
    /**
    * 轮播图链接
    */
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String link;
    
    /**
    * 备注
    */
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")private String remark;
    
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
