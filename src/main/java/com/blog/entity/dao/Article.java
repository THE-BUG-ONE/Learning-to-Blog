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
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article")
@Accessors(chain = true)
public class Article{

    /**
    * 文章id
    */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message="[文章id]不能为空")
    private Integer id;
    
    /**
    * 作者id
    */
    @NotNull(message="[作者id]不能为空")
    private Integer userId;
    
    /**
    * 分类id
    */
    @NotNull(message="[分类id]不能为空")
    private Integer categoryId;
    
    /**
    * 缩略图
    */
    @NotBlank(message="[缩略图]不能为空")
    @Size(max= 1024,message="编码长度不能超过1024")
    @Length(max= 1024,message="编码长度不能超过1,024")
    private String articleCover;
    
    /**
    * 文章标题
    */
    @NotBlank(message="[文章标题]不能为空")
    @Size(max= 50,message="编码长度不能超过50")
    @Length(max= 50,message="编码长度不能超过50")
    private String articleTitle;
    
    /**
    * 文章摘要
    */
    @NotBlank(message="[文章摘要]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    @Length(max= 100,message="编码长度不能超过100")
    private String articleDesc;
    
    /**
    * 文章内容
    */
    @NotBlank(message="[文章内容]不能为空")
    private String articleContent;
    
    /**
    * 类型 (1原创 2转载 3翻译)
    */
    @NotNull(message="[类型 (1原创 2转载 3翻译)]不能为空")
    private Integer articleType;
    
    /**
    * 是否置顶 (0否 1是）
    */
    @NotNull(message="[是否置顶 (0否 1是）]不能为空")
    private Integer isTop;
    
    /**
    * 状态 (0发表 1草稿)
    */
    @NotNull(message="[状态 (0发表 1草稿)]不能为空")
    private Integer status;
    
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long likeCount;
    
    /**
    * 发表时间
    */
    @NotNull(message="[发表时间]不能为空")
    private Date createTime;
    
    /**
    * 更新时间
    */
    private Date updateTime;
    
    /**
    * 
    */
    @NotNull(message="[]不能为空")
    private Long viewCount;

    public Article(Integer id, Long likeCount, Long viewCount) {
        this.id = id;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}
