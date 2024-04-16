package com.blog.controller;

import com.framework.Result;
import com.framework.entity.vo.request.LoginReq;
import com.framework.service.BlogLoginService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginReq loginReq) {
        String res = blogLoginService.login(loginReq);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
