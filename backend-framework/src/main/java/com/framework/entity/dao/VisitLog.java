package com.framework.entity.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (VisitLog)表实体类
 *
 * @author makejava
 * @since 2024-04-09 14:26:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("visit_log")
public class VisitLog {
    @TableId
    //记录id    
    private Integer id;
    //访问页面
    private String page;
    //访问ip
    private String ipAddress;
    //访问地址
    private String ipSource;
    //操作系统
    private String os;
    //浏览器
    private String browser;
    //访问时间
    private Date createTime;
}

