package com.blog.entity.dao;


import java.util.Date;
import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message{

    /**
    * 留言id
    */
    @TableId
    @NotNull(message="[留言id]不能为空")
    private Integer id;
    
    /**
    * (0 留言/其他 回复评论id)
    */
    @NotNull(message="[(0 留言/其他 回复评论id)]不能为空")
    private Integer parentId;
    
    /**
    * 根评论id
    */
    @NotNull(message="[根评论id]不能为空")
    private Integer rootId;
    
    /**
    * 回复评论用户id
    */
    @NotNull(message="[回复评论用户id]不能为空")
    private Integer fromUserId;
    
    /**
    * 用户id
    */
    @NotNull(message="[用户id]不能为空")
    private Integer userId;
    
    /**
    * 留言内容
    */
    @NotBlank(message="[留言内容]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @Length(max= 255,message="编码长度不能超过255")private String message;
    
    /**
    * 是否通过 (0否 1是)
    */
    @NotNull(message="[是否通过 (0否 1是)]不能为空")
    private Integer isCheck;
    
    /**
    * 留言时间
    */
    @NotNull(message="[留言时间]不能为空")
    private Date createTime;
    
    /**
    * 更新时间
    */
    private Date updateTime;

    public Message(Integer parentId, Integer rootId, Integer fromUserId, Integer userId, String message) {
        this.parentId = parentId;
        this.rootId = rootId;
        this.fromUserId = fromUserId;
        this.userId = userId;
        this.message = message;
    }
}
