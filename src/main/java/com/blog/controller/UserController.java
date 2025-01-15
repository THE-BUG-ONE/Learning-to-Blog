package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.EmailReq;
import com.blog.entity.vo.request.UserInfoReq;
import com.blog.entity.vo.request.UserReq;
import com.blog.entity.vo.response.UserInfoResp;
import com.blog.entity.vo.response.UserOptionResp;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @SystemLog(businessName = "修改用户头像")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(@NotNull @RequestPart("file") MultipartFile file) {
        String res = userService.updateUserAvatar(file);
        return Result.result(res);
    }

    @SystemLog(businessName = "修改用户邮箱")
    @PutMapping("/email")
    public Result<?> updateEmail(@RequestBody @Valid EmailReq emailReq) {
        userService.updateEmail(emailReq);
        return Result.success();
    }

    @SystemLog(businessName = "获取登录用户信息")
    @GetMapping("/getUserInfo")
    public Result<UserInfoResp> getUserInfo() {
        UserInfoResp res = userService.getUserInfo();
        return Result.result(res);
    }

    @SystemLog(businessName = "修改用户信息")
    @PutMapping("/info")
    public Result<?> updateInfo(@RequestBody @Valid UserInfoReq userInfoReq) {
        userService.updateInfo(userInfoReq);
        return Result.success();
    }

    @SystemLog(businessName = "修改用户密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody @Valid UserReq userReq) {
        userService.updatePassword(userReq);
        return Result.success();
    }

    @SystemLog(businessName = "查询关联用户列表")
    @GetMapping("/search")
    public Result<List<UserOptionResp>> getUserOptionList(@NotNull String username) {
        List<UserOptionResp> res = userService.getUserOptionList(username);
        return Result.result(res);
    }
}
