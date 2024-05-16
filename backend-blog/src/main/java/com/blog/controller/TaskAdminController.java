package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.TaskReq;
import com.framework.service.TaskService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/task")
public class TaskAdminController {

    @Resource
    private TaskService taskService;

    @SystemLog(businessName = "添加定时任务")
    @PostMapping("/add")
    public Result<?> addTask(@RequestBody @Valid TaskReq taskReq) {
        taskService.addTask(taskReq);
        return Result.success();
    }
}
