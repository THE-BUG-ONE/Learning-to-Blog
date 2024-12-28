package com.blog.utils;

import com.blog.annotation.SystemLog;
import com.blog.constants.SystemConstants;
import com.blog.entity.vo.Result;
import com.blog.entity.dao.LoginUser;
import com.blog.entity.dao.User;
import com.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class WebUtils {

    @Lazy
    @Resource
    private UserService userService;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser"))
            return userService.getById(SystemConstants.TEST_ID);
        LoginUser loginUser = (LoginUser) principal;
        return loginUser.getUser();
    }
}
