package com.zjx.courese.authserver.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 定义控制器方法的切入点
    @Pointcut("execution(* com.zjx.courese.authserver.controller.*.*(..))")
    public void controllerMethods() {
        // 切入点方法，无需实现
    }

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object[] args = joinPoint.getArgs();
        logger.info("进入方法: {} 参数: {}", joinPoint.getSignature().toShortString(), args);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("方法执行异常: {} 参数: {}", joinPoint.getSignature().toShortString(), args, e);
            throw e;
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("退出方法: {} 结果: {} 耗时: {} 毫秒", joinPoint.getSignature().toShortString(), result, timeTaken);

        return result;
    }
}
