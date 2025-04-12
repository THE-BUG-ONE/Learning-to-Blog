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
@TableName("site_config")
public class SiteConfig{

    /**
    * 主键
    */
    @TableId
    @NotNull(message="[主键]不能为空")
    private Integer id;
    
    /**
    * 用户头像
    */
    @NotBlank(message="[用户头像]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String userAvatar;
    
    /**
    * 游客头像
    */
    @NotBlank(message="[游客头像]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String touristAvatar;
    
    /**
    * 文章默认封面
    */
    @NotBlank(message="[文章默认封面]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String articleCover;
    
    /**
    * 是否评论审核 (0否 1是)
    */
    @NotNull(message="[是否评论审核 (0否 1是)]不能为空")
    private Integer commentCheck;
    
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
