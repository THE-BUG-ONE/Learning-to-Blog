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
@TableName("notice")
public class Notice{

    /**
    * 公告id
    */
    @TableId
    @NotNull(message="[]不能为空")
    private Integer id;
    
    /**
    * 公告标题
    */
    @NotBlank(message="[公告标题]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String title;
    
    /**
    * 公告内容
    */
    @NotBlank(message="[公告内容]不能为空")
    private String content;
    
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
