package com.framework.aspect;

import com.alibaba.fastjson2.JSON;
import com.framework.annotation.SystemLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.framework.annotation.SystemLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
        Object ret;
        try {
            //方法前增强操作
            handleBefore(pjp);
            //运行方法
            ret = pjp.proceed();
            //方法后增强操作
            handleAfter(ret);
        } finally {
            //结束后换行
            log.info("=========END=========" + System.lineSeparator());
        }
        return ret;
    }

    private void handleBefore(ProceedingJoinPoint pjp) {
        //获取切口签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        //从请求应用程序上下文获取请求报文内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //切口方法权限为private时值为null
        //断言变量不为空
        assert requestAttributes != null;
        //获取请求内容
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(methodSignature);

        log.info("=========START=========");
        // 打印请求URL
        log.info("URL             : {}", request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName    : {}", systemLog.businessName());
        // 打印
        log.info("HTTP Method     : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method    : {},{}", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        // 打印请求的 IP
        log.info("IP              : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args    : {}", JSON.toJSONString(pjp.getArgs()));
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response        : {}", JSON.toJSONString(ret));
    }

    private SystemLog getSystemLog(MethodSignature methodSignature) {
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }
}
