package com.generic.ecom.controller;

import com.generic.ecom.model.Products;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class loginPage {
    private final static String RESPONSE_ATTR_NAME = "response";
    @PostMapping("/login")
    public Map<String,String> login(HttpServletRequest request){
        // spring security will have this endpoint exposed for us but we are overriding it for our own implementation
        // to override this endpoint we need to configure the filter chain also refer the securityConfig.java if we didn't override
        // the default filter chain spring will always its own implementation for login page.
        System.out.println("Login Request received");
        Map<String, String> response = new HashMap<>();
        response.put("sessionId",request.getSession().getId());
        response.put("CSRFtoken", ((CsrfToken)request.getAttribute("_csrf")).getToken()
        );
        return  response;
    }
}
