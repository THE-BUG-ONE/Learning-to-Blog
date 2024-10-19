package com.blog.service;

import com.blog.entity.vo.request.LoginReq;
import com.blog.entity.vo.request.RegisterReq;

public interface BlogLoginService {
    String login(LoginReq loginReq);

    void logout();

    void register(RegisterReq registerReq);

    void code(String username, String type);
}
