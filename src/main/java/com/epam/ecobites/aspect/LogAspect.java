package com.epam.ecobites.aspect;

import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class LogAspect {

    @Pointcut("within(com.epam.ecobites.controller..*)")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getSourceLocation().getWithinType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("Executing request: Inside: " + className
            + " class and executing: " + methodName + " method with arguments: "
            + Arrays.toString(args));
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSourceLocation().getWithinType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("After successful execution of: " + className
            + " class and " + methodName + " method, the return value: " + result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getSourceLocation().getWithinType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("After unsuccessful execution of: " + className
            + " class and " + methodName + " method, the exception "
            + "message is: " + exception.getMessage());
    }
}