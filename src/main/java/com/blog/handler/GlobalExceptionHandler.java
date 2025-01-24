package com.blog.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.blog.entity.vo.Result;
import com.blog.utils.WebUtils;
import com.blog.utils.enums.AppHttpCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BasicErrorController {

    @Resource
    private WebUtils webUtils;

    public GlobalExceptionHandler() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public void tokenExpiredExceptionHandler(HttpServletResponse response, TokenExpiredException e) throws IOException {
        log.error("Token验证异常:{}", e.getMessage());
        webUtils.renderString(response, Result.failure(AppHttpCodeEnum.FORBIDDEN));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadableExceptionHandler(HttpServletResponse response, HttpMessageNotReadableException e) throws IOException {
        log.error("请求参数校验异常:{}", e.getMessage());
        webUtils.renderString(response, Result.failure(AppHttpCodeEnum.BAD_REQUEST));
    }

    @ExceptionHandler(RuntimeException.class)
    public void runtimeExceptionHandler(HttpServletResponse response, RuntimeException e) throws IOException {
        log.error("出现异常:", e);
        webUtils.renderString(response,
                Result.failure(AppHttpCodeEnum.INTERNAL_ERROR.getCode(), e.getMessage()));
    }

}
