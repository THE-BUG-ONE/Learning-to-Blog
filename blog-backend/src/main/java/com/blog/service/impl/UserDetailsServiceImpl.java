package com.blog.service.impl;

import com.blog.entity.dao.LoginUser;
import com.blog.entity.dao.User;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        //用户不存在则抛出异常
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("用户不存在");
        //权限信息封装
        user.setPassword(user.getPassword());
        return new LoginUser(user);
    }
}
