package com.generic.ecom.controller;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public UserRepo userRepo;

    BCryptPasswordEncoder passEncoder=new BCryptPasswordEncoder(6);

    @PostMapping("/signIn")
    public Map<String,String> signIn(@RequestBody Users user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepo.save(user);
        Map<String,String> response=new HashMap<>();
        response.put(RESPONSE_ATTR_NAME,"Account Created Sucessfully");
        return response;
    }
}
