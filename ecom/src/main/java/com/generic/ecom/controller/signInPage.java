package com.generic.ecom.controller;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import com.generic.ecom.view.security.SignInAndLoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class signInPage {
    private final static String RESPONSE_ATTR_NAME = "response";

    @Autowired
    private SignInAndLoginService signInAndLoginService;


    @PostMapping("/signIn")
    public Map<String,String> signIn(@RequestBody Users user){
        return signInAndLoginService.signIn(user);
    }

    @PostMapping("/login")
    public Map<String,String> login(HttpServletRequest request){
        // spring security will have this endpoint exposed for us but we are overriding it for our own implementation
        // to override this endpoint we need to configure the filter chain also refer the securityConfig.java if we didn't override
        // the default filter chain spring will always its own implementation for login page.
        return  signInAndLoginService.verify(request);
    }
}
