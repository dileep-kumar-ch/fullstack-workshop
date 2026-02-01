package com.example.revpay_p2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Runs before any method in service package
    @Before("execution(* com.example.revpay_p2.service.*.*(..))")
    public void beforeServiceMethods(JoinPoint joinPoint) {
        System.out.println("👉 Entering method: " + joinPoint.getSignature());
    }

    // Runs after any method in service package
    @After("execution(* com.example.revpay_p2.service.*.*(..))")
    public void afterServiceMethods(JoinPoint joinPoint) {
        System.out.println("✅ Exiting method: " + joinPoint.getSignature());
    }
}
