package com.framework.utils;

import com.framework.Result;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebUtils {

    public <T> void renderString(HttpServletResponse response, Result<T> result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(result.code());
        response.getWriter().write(result.asJsonString());
    }
}
