package com.framework.handler;

import com.framework.Result;
import com.framework.utils.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error("出现异常:", e);
        return Result.failure(AppHttpCodeEnum.INTERNAL_ERROR.getCode(), e.getMessage());
    }
}
