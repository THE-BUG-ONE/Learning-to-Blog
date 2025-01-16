package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.DisableReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.request.UserInfoReq;
import com.blog.entity.vo.response.*;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

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
    public Result<PageResult<UserBackResp>> getBackUserList(@Valid PageReq pageReq) {
        PageResult<UserBackResp> res = userService.getBackUserList(pageReq);
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
    public Result<UserBackResp> updateUser(@RequestBody @Valid UserInfoReq userInfoReq) {
        UserBackResp res = userService.updateUser(userInfoReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "删除用户")
    @DeleteMapping("/delete")
    public Result<?> deleteUser(@RequestBody @Valid List<Integer> userIdList) {
        userService.deleteUser(userIdList);
        return Result.success();
    }
}
