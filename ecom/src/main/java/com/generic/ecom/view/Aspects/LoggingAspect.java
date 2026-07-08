package com.generic.ecom.view.Aspects;
import com.generic.ecom.controller.HomepageController;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(* com.generic.ecom.controller.HomepageController.*(..))")
    public static void logBeforeMethod() {
        System.out.println("Executing before the method in HomePageController...");
    }

    @After("execution(* com.generic.ecom.controller.HomepageController.*(..))")
    public static void logAfterMethod() {
        System.out.println("Executing after the method in HomepageController ...");
    }
}