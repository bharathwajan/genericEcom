package com.generic.ecom.controller;

import com.generic.ecom.model.Users;
import com.generic.ecom.view.security.SiginAndLoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginAndSignIn {

    @Autowired
    public SiginAndLoginService siginAndLoginService; // views(service) layer

    @PostMapping("/signIn")
    public Map<String,String> signIn(@RequestBody Users user){
        return siginAndLoginService.signIn(user);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Users user, HttpServletRequest request){
        // spring security will have this endpoint exposed for us but we are overriding it for our own implementation
        // to override this endpoint we need to configure the filter chain also refer the securityConfig.java if we didn't override
        // the default filter chain spring will always its own implementation for login page.
        System.out.println("Login Request received");
        return siginAndLoginService.verify(user,request);
        // previously
        // the authentication was handled by spring security where the username and password is send thriugh authentication tab in postman
        // even with implementation if someone send creds via authentication tab it will still work
        // but it wont work if some send userName password in body of the request
        // to make it work we had written a custom logic in verify method

    }
}
