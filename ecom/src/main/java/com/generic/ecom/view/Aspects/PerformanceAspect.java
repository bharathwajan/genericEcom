package com.generic.ecom.view.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    // @annotation(TrackTime) means this advice will be applied to methods annotated with @TrackTime --> refer customAnnotation project
    @Around("@annotation(TrackTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // this invokes the method
        long duration = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature().getName() + " took " + duration + "ms");
        return result;
    }
}
