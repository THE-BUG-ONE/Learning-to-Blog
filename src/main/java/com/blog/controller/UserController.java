package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.response.UserOptionResp;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @SystemLog(businessName = "获取关联用户列表")
    @GetMapping("/search")
    public Result<List<UserOptionResp>> getUserOptionList(@NotNull String username) {
        List<UserOptionResp> res = userService.getUserOptionList(username);
        return Result.result(res);
    }
}
