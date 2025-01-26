package com.blog.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.blog.annotation.ExceptionLog;
import com.blog.entity.vo.Result;
import com.blog.utils.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler extends BasicErrorController {

    public GlobalExceptionHandler() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @ExceptionLog(businessName = "Token验证异常")
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<String> tokenExpiredExceptionHandler(TokenExpiredException e) {
        assert e != null;
        return Result.failure(AppHttpCodeEnum.FORBIDDEN);
    }

    @ExceptionLog(businessName = "请求参数校验异常")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, HandlerMethodValidationException.class})
    public Result<String> httpMessageNotReadableExceptionHandler(Exception e) {
        assert e != null;
        return Result.failure(AppHttpCodeEnum.BAD_REQUEST);
    }

    @ExceptionLog(businessName = "未知异常")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("出现异常:", e);
        return Result.failure(AppHttpCodeEnum.INTERNAL_ERROR);
    }

}
