package com.blog.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
* 
* @TableName task_log
*/
@AllArgsConstructor
@NoArgsConstructor
public class TaskLog implements Serializable {

    /**
    * 任务日志id
    */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message="[任务日志id]不能为空")
    @ApiModelProperty("任务日志id")
    private Integer id;
    /**
    * 任务名称
    */
    @NotBlank(message="[任务名称]不能为空")
    @Size(max= 64,message="编码长度不能超过64")
    @ApiModelProperty("任务名称")
    @Length(max= 64,message="编码长度不能超过64")
    private String taskName;
    /**
    * 任务组名
    */
    @NotBlank(message="[任务组名]不能为空")
    @Size(max= 64,message="编码长度不能超过64")
    @ApiModelProperty("任务组名")
    @Length(max= 64,message="编码长度不能超过64")
    private String taskGroup;
    /**
    * 调用目标字符串
    */
    @NotBlank(message="[调用目标字符串]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("调用目标字符串")
    @Length(max= 255,message="编码长度不能超过255")
    private String invokeTarget;
    /**
    * 日志信息
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("日志信息")
    @Length(max= 255,message="编码长度不能超过255")
    private String taskMessage;
    /**
    * 执行状态 (0失败 1正常)
    */
    @ApiModelProperty("执行状态 (0失败 1正常)")
    private Integer status;
    /**
    * 错误信息
    */
    @Size(max= -1,message="编码长度不能超过-1")
    @ApiModelProperty("错误信息")
    @Length(max= -1,message="编码长度不能超过-1")
    private String errorInfo;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
    * 任务日志id
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 任务名称
    */
    private void setTaskName(String taskName){
    this.taskName = taskName;
    }

    /**
    * 任务组名
    */
    private void setTaskGroup(String taskGroup){
    this.taskGroup = taskGroup;
    }

    /**
    * 调用目标字符串
    */
    private void setInvokeTarget(String invokeTarget){
    this.invokeTarget = invokeTarget;
    }

    /**
    * 日志信息
    */
    private void setTaskMessage(String taskMessage){
    this.taskMessage = taskMessage;
    }

    /**
    * 执行状态 (0失败 1正常)
    */
    private void setStatus(Integer status){
    this.status = status;
    }

    /**
    * 错误信息
    */
    private void setErrorInfo(String errorInfo){
    this.errorInfo = errorInfo;
    }

    /**
    * 创建时间
    */
    private void setCreateTime(Date createTime){
    this.createTime = createTime;
    }


    /**
    * 任务日志id
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 任务名称
    */
    private String getTaskName(){
    return this.taskName;
    }

    /**
    * 任务组名
    */
    private String getTaskGroup(){
    return this.taskGroup;
    }

    /**
    * 调用目标字符串
    */
    private String getInvokeTarget(){
    return this.invokeTarget;
    }

    /**
    * 日志信息
    */
    private String getTaskMessage(){
    return this.taskMessage;
    }

    /**
    * 执行状态 (0失败 1正常)
    */
    private Integer getStatus(){
    return this.status;
    }

    /**
    * 错误信息
    */
    private String getErrorInfo(){
    return this.errorInfo;
    }

    /**
    * 创建时间
    */
    private Date getCreateTime(){
    return this.createTime;
    }

}
