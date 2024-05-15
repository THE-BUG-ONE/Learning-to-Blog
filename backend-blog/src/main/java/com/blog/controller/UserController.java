package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.EmailReq;
import com.framework.entity.vo.request.UserInfoReq;
import com.framework.entity.vo.request.UserReq;
import com.framework.entity.vo.response.UserInfoResp;
import com.framework.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @SystemLog(businessName = "修改用户头像")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(@RequestPart("file") MultipartFile file) {
        String res = userService.updateUserAvatar(file);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "修改用户邮箱")
    @PutMapping("/email")
    public Result<?> updateEmail(@RequestBody EmailReq emailReq) {
        userService.updateEmail(emailReq);
        return Result.success();
    }

    @SystemLog(businessName = "获取登录用户信息")
    @GetMapping("/getUserInfo")
    public Result<UserInfoResp> getUserInfo() {
        UserInfoResp res = userService.getUserInfo();
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping("/info")
    public Result<?> updateInfo(@RequestBody UserInfoReq userInfoReq) {
        userService.updateInfo(userInfoReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改用户密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@Validated @RequestBody UserReq userReq) {
        userService.updatePassword(userReq);
        return Result.success();
    }
}
