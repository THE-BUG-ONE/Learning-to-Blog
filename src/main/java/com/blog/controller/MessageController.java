package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.service.MessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @SystemLog(businessName = "获取留言列表")
    @GetMapping("/list")
    public Result<PageResult<MessageResp>> getMessageList(@Valid PageReq req) {
        PageResult<MessageResp> res = messageService.getMessageList(req);
        return Result.result(res);
    }

    @SystemLog(businessName = "获取留言回复列表")
    @GetMapping("/reply-list")
    public Result<List<MessageResp>> getMessageReplyList(@NotNull Integer rootId) {
        List<MessageResp> res = messageService.getMessageReplyList(rootId);
        return Result.result(res);
    }
}
