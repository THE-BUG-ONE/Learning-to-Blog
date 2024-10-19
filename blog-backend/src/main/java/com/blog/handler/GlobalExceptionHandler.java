package com.blog.handler;

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

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        log.error("出现异常:", e);
        String msg = !Pattern.matches(".*?[\u4E00-\u9FA5].*?", e.getMessage().substring(0, 1)) ?
                getStatus(request).toString().substring(4) :
                e.getMessage();
        webUtils.renderString(response, Result.failure(getStatus(request).value(), msg));
    }
}
