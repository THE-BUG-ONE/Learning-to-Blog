package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.FriendBackReq;
import com.blog.entity.vo.request.FriendReq;
import com.blog.entity.vo.response.FriendBackResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.service.FriendService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/friend")
public class FriendAdminController {

    @Resource
    private FriendService friendService;

    @SystemLog(businessName = "添加友链")
    @PostMapping("/add")
    public Result<?> addFriend(@RequestBody @Valid FriendReq friendReq) {
        friendService.addFriend(friendReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除友链")
    @DeleteMapping("/delete")
    public Result<?> deleteFriend(@RequestBody @Valid List<Integer> friendIdList) {
        friendService.deleteFriend(friendIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看友链后台列表")
    @GetMapping("/list")
    public Result<PageResult<FriendBackResp>> getBackFriendList(
            @RequestBody @Valid FriendBackReq friendBackReq) {
        PageResult<FriendBackResp> res = friendService.getBackFriendList(friendBackReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "修改友链")
    @PutMapping("/update")
    public Result<?> updateFriend(@RequestBody @Valid FriendReq friendReq) {
        friendService.updateFriend(friendReq);
        return Result.success();
    }
}
