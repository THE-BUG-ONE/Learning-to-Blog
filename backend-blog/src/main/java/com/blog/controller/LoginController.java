package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.LoginReq;
import com.framework.entity.vo.request.RegisterReq;
import com.framework.service.BlogLoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Resource
    private BlogLoginService blogLoginService;

    //接口：用户登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginReq loginReq) {
        if (!StringUtils.hasText(loginReq.getUsername()))
            throw new RuntimeException("用户名为空");
        String res = blogLoginService.login(loginReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //接口：用户退出
    @GetMapping("/logout")
    public Result<?> logout() {
        blogLoginService.logout();
        return Result.success();
    }

    //接口：用户邮箱注册
    @PostMapping("/register")
    public Result<?> register(@RequestBody @Validated RegisterReq registerReq) {
        blogLoginService.register(registerReq);
        return Result.success();
    }

    //接口：发送邮箱验证码
    @GetMapping("/code")
    public Result<?> code(@Validated String username, HttpServletRequest request) {
        blogLoginService.code(username, request.getRemoteAddr());
        return Result.success();
    }
}
