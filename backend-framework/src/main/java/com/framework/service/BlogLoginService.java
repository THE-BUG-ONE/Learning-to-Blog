package com.framework.service;

import com.framework.entity.vo.request.LoginReq;

public interface BlogLoginService {
    String login(LoginReq loginReq);

    void logout();
}
