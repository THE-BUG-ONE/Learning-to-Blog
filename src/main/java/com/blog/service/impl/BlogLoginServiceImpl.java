package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.LoginUser;
import com.blog.entity.dao.User;
import com.blog.entity.dao.UserRole;
import com.blog.entity.vo.request.LoginReq;
import com.blog.entity.vo.request.RegisterReq;
import com.blog.service.BlogLoginService;
import com.blog.service.SiteConfigService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.utils.FlowUtils;
import com.blog.utils.JwtUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Lazy
    @Resource
    private UserRoleService userRoleService;

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

    @Resource
    private WebUtils webUtils;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    @Transactional
    public String login(LoginReq loginReq) {
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authentication))
                return null;
            //生成token
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            int userId = loginUser.getUser().getId();
            //更新登录时间
            if (!userService.lambdaUpdate()
                    .eq(User::getId, userId)
                    .set(User::getLoginTime, DateTime.now())
                    .update())
                throw new RuntimeException("登录异常:[更新登录时间错误]");

            //前端已添加前缀
            String token = SystemConstants.JWT_HEAD + jwtUtils.createJwt(loginUser, userId);
            //将loginUser按id存入redis
            redisTemplate.opsForValue().set(
                    SystemConstants.JWT_REDIS_KEY + userId,
                    loginUser,
                    SystemConstants.JWT_EXPIRE,
                    TimeUnit.DAYS);
            // 已登录用户ID存入redis
            stringRedisTemplate.opsForSet()
                    .add(SystemConstants.LOGGED_USER_ID, String.valueOf(userId));
            return token;
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void logout() {
        //获取userId
        int userId = webUtils.getRequestUser().getId();
        //删除redis中的用户信息
        redisTemplate.delete(SystemConstants.JWT_REDIS_KEY + userId);
        stringRedisTemplate.opsForSet().remove(SystemConstants.LOGGED_USER_ID, String.valueOf(userId));
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    @Transactional
    public void register(RegisterReq req) {
        try {
            String username = req.getUsername();
            if (userService.lambdaQuery().eq(User::getUsername, username).exists())
                throw new RuntimeException("注册用户异常:[用户名已存在]");
            String code = req.getCode();
            //校验验证码
            String msg = userService.emailCodeCheck(req.getEmail(), code, SystemConstants.REGISTER_CODE);
            if (msg != null)
                throw new RuntimeException(msg);
            String password = encoder.encode(req.getPassword());
            // 添加 用户表|用户权限表|权限表 信息
            if (
                    userService.save(new User(
                            req.getNickname(), username, password,
                            siteConfigService.getSiteConfig().getUserAvatar(),
                            req.getEmail(), 0)) &&
                            userRoleService.save(new UserRole(userService.lambdaQuery()
                                    .eq(User::getEmail, req.getEmail())
                                    .one()
                                    .getId(), SystemConstants.USER_ROLE_TEST)))
                stringRedisTemplate.delete(SystemConstants.VERIFY_EMAIL_DATA + username);
            else throw new RuntimeException("注册用户异常:[未知异常]");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void code(String email, String type) {
        String ip = webUtils.getRequest().getRemoteAddr();
        synchronized (ip.intern()) {
            if (flowUtils.limitOnceCheck(SystemConstants.VERIFY_EMAIL_LIMIT + ip, 60))
                throw new RuntimeException("验证码异常:[请求频繁，请稍后再试]");
            Random random = new Random();
            String code = String.valueOf(random.nextInt(899999) + 100000);
            sendEmailMessage(email, code, type);
            stringRedisTemplate.opsForValue().set(SystemConstants.VERIFY_EMAIL_DATA + email, code, 3, TimeUnit.MINUTES);
        }
    }

    private void sendEmailMessage(String email, String code, String type) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = switch (type) {
            case SystemConstants.REGISTER_CODE -> "当前邮件为注册邮件";
            case SystemConstants.EMAIL_RESET_CODE -> "当前邮件为邮箱更改邮件";
            case SystemConstants.PASSWORD_RESET_CODE -> "当前邮件为密码重置邮件";
            default -> null;
        };
        message.setFrom(username);
        message.setTo(email);
        message.setText("验证码:" + code + "\n当前验证码有效时间为3分钟");
        message.setSubject(subject);
        mailSender.send(message);
    }
}
