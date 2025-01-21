package com.blog.handler;

import com.blog.entity.vo.Result;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
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

    @ExceptionHandler(RuntimeException.class)
    public void runtimeExceptionHandler(HttpServletResponse response, RuntimeException e) throws IOException {
        log.error("出现异常:", e);
        String msg = e.getMessage();
        webUtils.renderString(response, Result.failure(500, msg));
        log.error(msg);
    }

}
