package com.framework.service.impl;

import com.framework.constants.SystemConstants;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.vo.request.LoginReq;
import com.framework.service.BlogLoginService;
import com.framework.service.UserService;
import com.framework.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PasswordEncoder encoder;

    @Override
    public String login(LoginReq loginReq) {
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authentication))
            throw new RuntimeException("用户名或密码错误");
        //生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        String token = SystemConstants.JWT_HEAD + JwtUtils.createJwt(loginUser, userId);
        //存入redis
        stringRedisTemplate.opsForValue().set(
                SystemConstants.JWT_REDIS_KEY + userId,
                token,
                SystemConstants.JWT_EXPIRE,
                TimeUnit.DAYS);
        System.out.println(token);
        return token;
    }


}
