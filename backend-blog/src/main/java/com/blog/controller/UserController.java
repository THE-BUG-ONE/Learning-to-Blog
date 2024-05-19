package com.blog.controller;

import com.framework.Result;
import com.framework.annotation.SystemLog;
import com.framework.entity.vo.request.DisableReq;
import com.framework.entity.vo.response.UserBackInfoResp;
import com.framework.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserService userService;

    @SystemLog(businessName = "修改用户状态")
    @PutMapping("/changeStatus")
    public Result<?> changeUserStatus(@RequestBody @Valid DisableReq disableReq) {
        userService.changeUserStatus(disableReq);
        return Result.success();
    }

    @SystemLog(businessName = "获取后台登录用户信息")
    @GetMapping("/getUserInfo")
    public Result<UserBackInfoResp> getUserInfo() {
        return null;
    }
}
