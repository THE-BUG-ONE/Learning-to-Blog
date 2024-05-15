package com.framework.service;

import com.framework.entity.vo.request.LoginReq;
import com.framework.entity.vo.request.RegisterReq;

public interface BlogLoginService {
    String login(LoginReq loginReq);

    void logout();

    void register(RegisterReq registerReq);

    void code(String username, String type);
}
