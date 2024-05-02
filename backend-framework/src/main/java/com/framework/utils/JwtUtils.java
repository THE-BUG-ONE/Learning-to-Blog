package com.framework.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.framework.constants.SystemConstants;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //创建JWT
    public String createJwt(UserDetails details, int userId) {
        return JWT.create()
                .withClaim("id", userId)
                .withClaim("username", details.getUsername())
                .withIssuedAt(DateTime.now())
                .withExpiresAt(DateUtil.offsetDay(DateTime.now(), SystemConstants.JWT_EXPIRE))
                .sign(Algorithm.HMAC256(SystemConstants.JWT_KEY));
    }

    //解析JWT
    public DecodedJWT resolveJwt(String token) {
        String jwt = convertToken(token);
        try {
            DecodedJWT verity = JWT
                    .require(Algorithm.HMAC256(SystemConstants.JWT_KEY))
                    .build()
                    .verify(jwt);
            if (isInvalidateToken(verity.getClaim("id").toString())) throw new RuntimeException("Token已拉黑");
            if (new Date().after(verity.getExpiresAt()))
                throw new TokenExpiredException("Token已过期", verity.getExpiresAtAsInstant());
            return verity;
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token验证错误");
        }
    }

    //判断jwt是否在黑名单中
    private boolean isInvalidateToken(String userId) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(SystemConstants.JWT_BLACK_LIST + userId));
    }

    //判断token头部是否带有Bearer
    private String convertToken(String token) {
        if (token == null || !token.startsWith("Bearer "))
            return null;
        return token.substring(7);
    }

    private JwtUtils() {
    }
}
