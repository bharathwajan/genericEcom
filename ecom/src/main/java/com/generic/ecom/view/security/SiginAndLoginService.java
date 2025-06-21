package com.generic.ecom.view.security;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SiginAndLoginService {
    /*
    This service is used to handle the login and sign in functionality.
     */

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepo userRepo;
    private final static String RESPONSE_ATTR_NAME = "response";
    BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(6);


    public Map<String, String> signIn(Users user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepo.save(user);
        Map<String, String> response = new HashMap<>();
        response.put(RESPONSE_ATTR_NAME, "Account Created Sucessfully");
        return response;
    }

    public Map<String, String> verify(Users user, HttpServletRequest request) {
        //login logic is handled by spring security by ecomUserDetailsService but created this for aesthetic purpose
        Map<String, String> response = new HashMap<>();
        Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (auth.isAuthenticated()) {
            response.put("sessionId", request.getSession().getId());
            response.put("CSRFtoken", ((CsrfToken) request.getAttribute("_csrf")).getToken());
            response.put(RESPONSE_ATTR_NAME, "Login Sucessfull");
        } else {
            response.put(RESPONSE_ATTR_NAME, "Login Failed");
        }
        return response;
    }
}
