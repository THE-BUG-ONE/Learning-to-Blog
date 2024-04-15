package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.response.FriendResp;
import com.framework.service.FriendService;
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
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
