package com.blog.controller;

import com.framework.Result;
import com.framework.entity.dao.LoginUser;
import com.framework.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //接口：修改用户头像
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestPart("file") MultipartFile file) {
        String res = userService.updateUserAvatar(loginUser, file);
        return res != null ?
                Result.success(res) :
                Result.failure();
    }
}
