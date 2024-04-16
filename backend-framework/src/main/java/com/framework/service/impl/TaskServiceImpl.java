package com.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.dao.Task;
import com.framework.entity.vo.request.TaskReq;
import com.framework.mapper.TaskMapper;
import com.framework.service.TaskService;
import com.framework.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * (Task)表服务实现类
 *
 * @author makejava
 * @since 2024-04-15 17:07:28
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    @Transactional
    public void addTask(TaskReq taskReq) {
        try {
            if (!this.save(BeanCopyUtils.copyBean(taskReq, Task.class)
                    .setCreateTime(DateUtil.date()))) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException("定时任务添加异常");
        }
    }
}

