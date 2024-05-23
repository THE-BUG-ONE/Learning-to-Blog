package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.PageReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.TalkResp;
import com.framework.service.TalkService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/talk", "/home/talk"})
public class TalkController {

    @Resource
    private TalkService talkService;

    @SystemLog(businessName = "查看首页说说")
    @GetMapping
    public Result<List<String>> getHomeTalk() {
        List<String> res = talkService.getHomeTalk();
        return Result.result(res);
    }

    @SystemLog(businessName = "查看说说列表")
    @GetMapping("/list")
    public Result<PageResult<TalkResp>> getTalkList(@Valid PageReq pageReq) {
        PageResult<TalkResp> res = talkService.getTalkList(pageReq);
        return Result.result(res);
    }
}
