package com.example.revpay_p2.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LogManager.getLogger(LoggingAspect.class);

    // Before service methods
    @Before("execution(* com.example.revpay_p2.service.*.*(..))")
    public void beforeServiceMethods(JoinPoint joinPoint) {
        log.info("👉 Entering method: {}", joinPoint.getSignature());
    }

    // After service methods
    @After("execution(* com.example.revpay_p2.service.*.*(..))")
    public void afterServiceMethods(JoinPoint joinPoint) {
        log.info("✅ Exiting method: {}", joinPoint.getSignature());
    }

    // When any exception occurs in service layer
    @AfterThrowing(
            pointcut = "execution(* com.example.revpay_p2.service.*.*(..))",
            throwing = "ex"
    )
    public void logExceptions(JoinPoint joinPoint, Exception ex) {

        log.error("❌ Exception in method: {} | Message: {}",
                joinPoint.getSignature(),
                ex.getMessage(),
                ex
        );
    }
}
