package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.entity.vo.Result;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

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

    @SystemLog(businessName = "修改用户状态")
    @PutMapping("/changeStatus")
    public Result<?> changeUserStatus(@RequestBody @Valid DisableReq disableReq) {
        userService.changeUserStatus(disableReq);
        return Result.success();
    }

    @SystemLog(businessName = "获取后台登录用户信息")
    @GetMapping("/getAdminUserInfo")
    public Result<UserBackInfoResp> getBackUserInfo() {
        UserBackInfoResp res = userService.getBackUserInfo();
        return Result.result(res);
    }

    @SystemLog(businessName = "获取后台用户列表")
    @GetMapping("/list")
    public Result<PageResult<UserBackResp>> getBackUserList(@Valid PageReq pageReq) {
        PageResult<UserBackResp> res = userService.getBackUserList(pageReq);
        return Result.result(res);
    }

    @SystemLog(businessName = "获取用户角色列表")
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
