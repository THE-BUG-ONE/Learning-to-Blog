package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.FriendBackReq;
import com.framework.entity.vo.request.FriendReq;
import com.framework.entity.vo.response.FriendBackResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/friend")
public class FriendAdminController {

    @Resource
    private FriendService friendService;

    @SystemLog(businessName = "添加友链")
    @PostMapping("/add")
    public Result<?> addFriend(@RequestBody @Validated FriendReq friendReq) {
        friendService.addFriend(friendReq);
        return Result.success();
    }

    @SystemLog(businessName = "删除友链")
    @DeleteMapping("/delete")
    public Result<?> deleteFriend(@RequestBody @Validated List<Integer> friendIdList) {
        friendService.deleteFriend(friendIdList);
        return Result.success();
    }

    @SystemLog(businessName = "查看友链后台列表")
    @GetMapping("/list")
    public Result<PageResult<FriendBackResp>> getBackFriendList(
            @RequestBody @Validated FriendBackReq friendBackReq) {
        PageResult<FriendBackResp> res = friendService.getBackFriendList(friendBackReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "修改友链")
    @PutMapping("/update")
    public Result<?> updateFriend(@RequestBody @Validated FriendReq friendReq) {
        friendService.updateFriend(friendReq);
        return Result.success();
    }
}
