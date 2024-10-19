package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Task;
import com.blog.entity.vo.request.TaskReq;

/**
 * (Task)表服务接口
 *
 * @author makejava
 * @since 2024-04-15 17:07:28
 */
public interface TaskService extends IService<Task> {

    void addTask(TaskReq taskReq);
}

