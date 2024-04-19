package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.LoginReq;
import com.framework.service.BlogLoginService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private BlogLoginService blogLoginService;

    //接口：用户登录
    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginReq loginReq) {
        String res = blogLoginService.login(loginReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }

    //
}
