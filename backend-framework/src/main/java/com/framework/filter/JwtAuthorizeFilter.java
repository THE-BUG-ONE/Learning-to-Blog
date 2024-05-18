package com.framework.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.LoginUser;
import com.framework.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取token
        String token = request.getHeader("token");
        //token为空表示从未登录，直接放行，不进行token校验
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析获取用户信息
        DecodedJWT jwt = jwtUtils.resolveJwt(token);
        String userId = jwt.getClaim("id").toString();
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue()
                .get(SystemConstants.JWT_REDIS_KEY + userId);
        //若用户信息为空
        if (Objects.isNull(loginUser)){
            handlerExceptionResolver.resolveException(request, response, null, new RuntimeException("登录已过期"));
            return;
        }

        //每周用户访问量+1
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(SystemConstants.USER_WEEK_VIEW_COUNT)))
            stringRedisTemplate.opsForValue()
                    .increment(SystemConstants.USER_WEEK_VIEW_COUNT, 1L);
        else
            stringRedisTemplate.opsForValue()
                    .set(SystemConstants.USER_VIEW_COUNT, "1", 7, TimeUnit.DAYS);
        //总用户访问量+1
        stringRedisTemplate.opsForValue().increment(SystemConstants.USER_VIEW_COUNT, 1L);

        //存入应用程序上下文
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
