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
public class LoginAndSignInController {
    private final static String RESPONSE_ATTR_NAME = "response";

    @Autowired
    private SignInAndLoginService signInAndLoginService;


    @PostMapping("/signIn")
    public Map<String,String> signIn(@RequestBody Users user){
        return signInAndLoginService.signIn(user);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Users user,HttpServletRequest request){
        return  signInAndLoginService.verify(user,request);
    }
}
