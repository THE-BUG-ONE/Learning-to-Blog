package com.framework.utils;

import com.framework.Result;
import com.framework.entity.dao.LoginUser;
import com.framework.entity.dao.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class WebUtils {

    public <T> void renderString(HttpServletResponse response, Result<T> result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(result.code());
        response.getWriter().write(result.asJsonString());
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;
    }

    public User getRequestUser() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUser.getUser();
    }
}
