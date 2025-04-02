package com.task.TaskManagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("execution(* com.task.TaskManagement.service..*(..))")
    public void allServiceMethods(){}

    @Before("allServiceMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Executing: {}", joinPoint.getSignature().toShortString());
    }
    @After("allServiceMethods()")
    public void logAfterMethod(JoinPoint joinPoint) {
        logger.info("Executed: {}", joinPoint.getSignature().toShortString());
    }
    @AfterReturning(pointcut = "execution(* com.task.TaskManagement.service.AuthService.*(..))", returning = "result")
    public void logAuthentication(JoinPoint joinPoint, Object result) {
        logger.info("Authentication method executed: {}", joinPoint.getSignature().toShortString());
        logger.info("Returned: {}", result);
    }

    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "throwable")
    public void logException(JoinPoint joinPoint, Throwable throwable) {
        logger.error("Exception in: {}", joinPoint.getSignature().toShortString());
        logger.error("Exception message: {}", throwable.getMessage());
    }
}
