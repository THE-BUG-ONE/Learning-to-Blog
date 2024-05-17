package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.CheckReq;
import com.framework.entity.vo.request.MessageBackReq;
import com.framework.entity.vo.response.MessageBackResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.MessageService;
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

    @SystemLog(businessName = "查看后台留言列表")
    @GetMapping("/list")
    public Result<PageResult<MessageBackResp>> getBackMessageList(@Valid MessageBackReq messageBackReq) {
        PageResult<MessageBackResp> res = messageService.getBackMessageList(messageBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "审核留言")
    @PutMapping("/pass")
    public Result<?> checkMessage(@RequestBody @Valid CheckReq checkReq) {
        messageService.checkMessage(checkReq);
        return Result.success();
    }
}
