package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.constants.SystemConstants;
import com.framework.entity.vo.request.LoginReq;
import com.framework.entity.vo.request.RegisterReq;
import com.framework.service.BlogLoginService;
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
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    @SystemLog(businessName = "用户退出")
    @GetMapping("/logout")
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
    public Result<?> code(@NotNull String username) {
        blogLoginService.code(username, SystemConstants.REGISTER_CODE);
        return Result.success();
    }
}
