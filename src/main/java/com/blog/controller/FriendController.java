package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.FriendResp;
import com.blog.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    //查看友链列表
    @GetMapping("/list")
    public Result<List<FriendResp>> getFriendList() {
        List<FriendResp> res = friendService.getFriendList();
        return Result.result(res);
    }
}
