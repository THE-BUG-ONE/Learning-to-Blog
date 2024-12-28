package com.blog.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.blog.entity.vo.Result;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.regex.Pattern;

//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BasicErrorController {

    @Resource
    private WebUtils webUtils;

    public GlobalExceptionHandler() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public void tokenExpiredExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException e) throws IOException {
        log.error("出现异常:", e);
        webUtils.renderString(response, Result.failure(402, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public void runtimeExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException e) throws IOException {
        log.error("出现异常:", e);
        String msg = Pattern.matches("^[1-5]\\d{2}", e.getMessage()) ?
                getStatus(request).toString() :
                e.getMessage();
        webUtils.renderString(response, Result.failure(Integer.parseInt(msg.substring(0,3)), msg.substring(4)));
    }

}
