package com.example.logbacktest.config.aop;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class RequestLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);
    
    @Pointcut("execution(* com.example.logbacktest.controller..*.*(..))")
    public void onRequest() {}

    @Before("onRequest()")
    public void beforeParameterLog(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info("======================================================================");
        logger.info("Request: {} - {} {}", getTimeStamp(), request.getRemoteAddr(), request.getRequestURI());
        logger.info("{} - {}", getDeclaringTypeName(joinPoint), getMethod(joinPoint).getName());

        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) logger.info("[INFO] no parameter");
        for (Object arg : args) {
            logger.info("[INFO] parameter value: {}", arg);
        }
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
    }

    private String getDeclaringTypeName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
