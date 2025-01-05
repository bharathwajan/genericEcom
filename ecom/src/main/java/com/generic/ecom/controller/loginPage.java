package com.generic.ecom.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginPage {
    @PostMapping("/login")
    public String login(HttpServletRequest request){
        // spring security will have this endpoint exposed for us but we are overriding it for our own implementation
        // to override this endpoint we need to configure the filter chain also refer the securityConfig.java if we didn't override
        // the default filter chain spring will always its own implementation for login page.
        return "welcome Bharathwajan "+request.getSession().getId();
    }
}
