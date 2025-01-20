package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.MessageAddReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageBackResp;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.service.MessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/message")
public class MessageAdminController {

    @Resource
    private MessageService messageService;

    @SystemLog(businessName = "删除留言")
    @DeleteMapping("/delete")
    public Result<?> deleteMessage(@RequestBody @NotNull List<Integer> messageIdList) {
        messageService.deleteMessage(messageIdList);
        return Result.success();
    }

    @SystemLog(businessName = "获取后台留言列表")
    @GetMapping("/list")
    public Result<PageResult<MessageBackResp>> getBackMessageList(@Valid PageReq req) {
        PageResult<MessageBackResp> res = messageService.getBackMessageList(req);
        return Result.result(res);
    }

    @SystemLog(businessName = "添加留言")
    @PostMapping("/add")
    public Result<MessageResp> addMessage(@RequestBody @Valid MessageAddReq messageReq) {
        MessageResp res = messageService.addMessage(messageReq);
        return Result.result(res);
    }
}
