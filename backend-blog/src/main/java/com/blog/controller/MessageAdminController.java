package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.CheckReq;
import com.framework.entity.vo.request.MessageBackReq;
import com.framework.entity.vo.response.MessageBackResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/message")
public class MessageAdminController {

    @Resource
    private MessageService messageService;

    //接口：删除留言
    @DeleteMapping("/delete")
    public Result<?> deleteMessage(@RequestBody @Validated List<Integer> messageIdList) {
        messageService.deleteMessage(messageIdList);
        return Result.success();
    }

    //接口：查看后台留言列表
    @GetMapping("/list")
    public Result<PageResult<MessageBackResp>> getBackMessageList(@Validated MessageBackReq messageBackReq) {
        PageResult<MessageBackResp> res = messageService.getBackMessageList(messageBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：审核留言
    @PutMapping("/pass")
    public Result<?> checkMessage(@RequestBody @Validated CheckReq checkReq) {
        messageService.checkMessage(checkReq);
        return Result.success();
    }
}
