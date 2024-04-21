package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.MessageReq;
import com.framework.entity.vo.response.MessageResp;
import com.framework.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    //接口：查看留言列表
    @GetMapping("/list")
    public Result<List<MessageResp>> getMessageList() {
        List<MessageResp> res = messageService.getMessageList();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：添加留言
    @PostMapping("/add")
    public Result<?> addMessage(@RequestBody @Validated MessageReq messageReq) {
        messageService.addMessage(messageReq);
        return Result.success();
    }
}
