package com.framework.service;

import com.framework.entity.vo.request.LoginReq;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BlogLoginService {
    String login(LoginReq loginReq);
}
