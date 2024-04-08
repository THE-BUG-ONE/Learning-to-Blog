package com.framework.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Message)表实体类
 *
 * @author makejava
 * @since 2024-04-08 14:23:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_message")
public class Message {
    @TableId
    //留言id    
    private Integer id;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //留言内容
    private String messageContent;
    //用户ip
    private String ipAddress;
    //用户地址
    private String ipSource;
    //是否通过 (0否 1是)
    private Integer isCheck;
    //留言时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

