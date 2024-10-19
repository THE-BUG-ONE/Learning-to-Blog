package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TalkResp;
import com.blog.service.TalkService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
