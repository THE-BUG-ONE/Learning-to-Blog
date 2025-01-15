package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.NoticeResp;
import com.blog.service.NoticeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @SystemLog(businessName = "获取系统公告")
    @GetMapping("/list")
    public Result<List<NoticeResp>> getSystemMsgList() {
        List<NoticeResp> res = noticeService.getSystemMsgList();
        return Result.result(res);
    }
}
