package com.framework.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.framework.constants.SystemConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    //创建JWT
    public static String createJwt(UserDetails details, int userId) {
        return JWT.create()
                .withClaim("id", userId)
                .withClaim("username", details.getUsername())
                .withIssuedAt(DateTime.now())
                .withExpiresAt(DateUtil.offsetDay(DateTime.now(), SystemConstants.JWT_EXPIRE))
                .sign(Algorithm.HMAC256(SystemConstants.JWT_KEY));
    }

    private JwtUtils() {
    }
}
