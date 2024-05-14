package com.framework.service.impl;

import cn.hutool.core.date.DateTime;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import com.framework.entity.vo.request.LoginReq;
import com.framework.entity.vo.request.RegisterReq;
import com.framework.service.BlogLoginService;
import com.framework.service.SiteConfigService;
import com.framework.service.UserService;
import com.framework.utils.FlowUtils;
import com.framework.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Lazy
    @Resource
    private UserService userService;

    @Lazy
    @Resource
    private SiteConfigService siteConfigService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private PasswordEncoder encoder;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private FlowUtils flowUtils;

    @Resource
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

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
        String token = SystemConstants.JWT_HEAD + jwtUtils.createJwt(loginUser, userId);
        //将loginUser按id存入redis
        redisTemplate.opsForValue().set(
                SystemConstants.JWT_REDIS_KEY + userId,
                loginUser,
                SystemConstants.JWT_EXPIRE,
                TimeUnit.DAYS);
        System.out.println(token);
        return token;
    }

    @Override
    public void logout() {
        //获取token并解析
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userId
        int userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisTemplate.delete(SystemConstants.JWT_REDIS_KEY + userId);
    }

    @Override
    public void register(RegisterReq req) {
        String username = req.getUsername();
        if (userService.lambdaQuery().eq(User::getEmail, username).exists())
            throw new RuntimeException("此邮箱地址已被注册");
        String code = req.getCode();
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(SystemConstants.VERIFY_EMAIL_DATA + username)))
            throw new RuntimeException("验证码不存在");
        if (!code.equals(stringRedisTemplate.opsForValue().get(SystemConstants.VERIFY_EMAIL_DATA + username)))
            throw new RuntimeException("验证码错误");
        String password = encoder.encode(req.getPassword());
        if (userService.save(new User(
                "None", username, password, siteConfigService.getSiteConfig().getUserAvatar(),
                username, 1, 0, DateTime.now())))
            stringRedisTemplate.delete(SystemConstants.VERIFY_EMAIL_DATA + username);
        else throw new RuntimeException("内部错误");
    }

    @Override
    public void code(String username, String ip) {
        synchronized (ip.intern()) {
            if (flowUtils.limitOnceCheck(SystemConstants.VERIFY_EMAIL_LIMIT + ip, 60))
                throw new RuntimeException("请求频繁，请稍后再试");
            Random random = new Random();
            String code = String.valueOf(random.nextInt(899999) + 100000);
            senEmailMessage(username, code);
            stringRedisTemplate.opsForValue().set(SystemConstants.VERIFY_EMAIL_DATA + username, code, 3, TimeUnit.MINUTES);
        }
    }

    private void senEmailMessage(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setText("验证码:" + code + "\n当前验证码有效时间为3分钟");
        message.setSubject("当前邮件为注册邮件");
        mailSender.send(message);
    }
}
