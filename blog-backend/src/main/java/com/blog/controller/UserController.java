package com.blog.controller;

import com.blog.entity.vo.Result;
import com.blog.annotation.SystemLog;
import com.blog.entity.vo.request.DisableReq;
import com.blog.entity.vo.request.UserBackReq;
import com.blog.entity.vo.request.UserRoleReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.UserBackInfoResp;
import com.blog.entity.vo.response.UserBackResp;
import com.blog.entity.vo.response.UserRoleResp;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<UserBackInfoResp> getBackUserInfo() {
        UserBackInfoResp res = userService.getBackUserInfo();
        return Result.result(res);
    }

    @SystemLog(businessName = "查看后台用户列表")
    @GetMapping("/list")
    public Result<PageResult<UserBackResp>> getBackUserList(@Valid UserBackReq userBackReq) {
        PageResult<UserBackResp> res = userService.getBackUserList(userBackReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "查看用户角色选项")
    @GetMapping("/role")
    public Result<List<UserRoleResp>> getUserRoleList() {
        List<UserRoleResp> res = userService.getUserRoleList();
        return Result.result(res);
    }

    @SystemLog(businessName = "修改用户")
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody @Valid UserRoleReq userRoleReq) {
        userService.updateUser(userRoleReq);
        return Result.success();
    }
}
