package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.MessageReq;
import com.blog.entity.vo.response.MessageResp;
import com.blog.service.MessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @SystemLog(businessName = "查看留言列表")
    @GetMapping("/list")
    public Result<List<MessageResp>> getMessageList() {
        List<MessageResp> res = messageService.getMessageList();
        return Result.result(res);
    }

    @SystemLog(businessName = "添加留言")
    @PostMapping("/add")
    public Result<?> addMessage(@RequestBody @Valid MessageReq messageReq) {
        messageService.addMessage(messageReq);
        return Result.success();
    }
}
