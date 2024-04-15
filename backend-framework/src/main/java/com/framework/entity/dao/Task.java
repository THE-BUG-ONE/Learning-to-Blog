package com.framework.entity.dao;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (Task)表实体类
 *
 * @author makejava
 * @since 2024-04-15 17:07:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_task")
public class Task {
    @TableId
    //任务id    
    private Integer id;
    //任务名称
    private String taskName;
    //任务组名
    private String taskGroup;
    //调用目标
    private String invokeTarget;
    //cron执行表达式
    private String cronExpression;
    //计划执行错误策略 (1立即执行 2执行一次 3放弃执行)
    private Integer misfirePolicy;
    //是否并发执行 (0否 1是)
    private Integer concurrent;
    //任务状态 (0运行 1暂停)
    private Integer status;
    //任务备注信息
    private String remark;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}

