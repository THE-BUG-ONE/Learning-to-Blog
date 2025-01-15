package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.MessageReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.service.MessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @SystemLog(businessName = "查看留言列表")
    @GetMapping("/list")
    public Result<PageResult<MessageResp>> getMessageList(@Valid PageReq req) {
        PageResult<MessageResp> res = messageService.getMessageList(req);
        return Result.result(res);
    }

    @SystemLog(businessName = "添加留言")
    @PostMapping("/add")
    public Result<?> addMessage(@RequestBody @Valid MessageReq messageReq) {
//        messageService.addMessage(messageReq);
        return Result.success();
    }
}
