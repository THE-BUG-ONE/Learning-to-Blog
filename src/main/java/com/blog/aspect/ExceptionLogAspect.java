package com.blog.aspect;

import com.blog.annotation.ExceptionLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class ExceptionLogAspect {

    @Pointcut("@annotation(com.blog.annotation.ExceptionLog)")
    public void ept() {
    }

    @After(value = "ept()")
    public void printLog(JoinPoint point) {
        //获取切口签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        //从请求应用程序上下文获取请求报文内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //切口方法权限为private时值为null
        //断言变量不为空
        assert requestAttributes != null;
        //获取请求内容
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        ExceptionLog systemLog = methodSignature.getMethod().getAnnotation(ExceptionLog.class);

        //获取异常信息
        Exception e = (Exception) point.getArgs()[0];

        log.warn("=========START=========");
        // 打印请求URL
        log.warn("URL             : {}", request.getRequestURL());
        // 打印描述信息
        log.warn("BusinessName    : {}", systemLog.businessName());
        // 打印调用 异常 的全路径以及执行方法
        log.warn("Class Method    : {},{}", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        // 打印请求的 IP
        log.warn("IP              : {}", request.getRemoteHost());
        // 打印异常信息
        log.warn("Message         : {}", e.getMessage());
        //结束后换行
        log.warn("==========END==========" + System.lineSeparator());
    }
}
