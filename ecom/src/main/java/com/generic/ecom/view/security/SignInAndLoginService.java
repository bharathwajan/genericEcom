package com.generic.ecom.view.security;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignInAndLoginService {
    private final static String RESPONSE_ATTR_NAME = "response";
    @Autowired
    public UserRepo userRepo;
    @Autowired
    private AuthenticationManager authManager;

    BCryptPasswordEncoder passEncoder=new BCryptPasswordEncoder(6);

    public Map<String,String> signIn(Users user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepo.save(user);
        Map<String,String> response=new HashMap<>();
        response.put(RESPONSE_ATTR_NAME,"Account Created Sucessfully");
        return response;
    }

    public Map<String,String> verify(Users user,HttpServletRequest request){
        Map<String, String> response = new HashMap<>();
        Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        // storing the authentication object in the security context so that the subsequent requests can access it and validate the user
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

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
