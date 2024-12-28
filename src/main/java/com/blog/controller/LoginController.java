package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.constants.SystemConstants;
import com.blog.entity.vo.request.LoginReq;
import com.blog.entity.vo.request.RegisterReq;
import com.blog.service.BlogLoginService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @SystemLog(businessName = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid LoginReq loginReq) {
        String res = blogLoginService.login(loginReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "用户退出")
    @PostMapping("/logout")
    public Result<?> logout() {
        blogLoginService.logout();
        return Result.success();
    }

    @SystemLog(businessName = "用户邮箱注册")
    @PostMapping("/register")
    public Result<?> register(@RequestBody @Valid RegisterReq registerReq) {
        blogLoginService.register(registerReq);
        return Result.success();
    }

    @SystemLog(businessName = "发送邮箱验证码")
    @GetMapping("/code")
    public Result<?> code(@RequestBody @NotNull String username) {
        blogLoginService.code(username, SystemConstants.REGISTER_CODE);
        return Result.success();
    }
}
