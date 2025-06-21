package com.generic.ecom.view.security;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignInAndLoginService {
    private final static String RESPONSE_ATTR_NAME = "response";
    @Autowired
    public UserRepo userRepo;
    BCryptPasswordEncoder passEncoder=new BCryptPasswordEncoder(6);

    public Map<String,String> signIn(Users user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepo.save(user);
        Map<String,String> response=new HashMap<>();
        response.put(RESPONSE_ATTR_NAME,"Account Created Sucessfully");
        return response;
    }

    public Map<String,String> verify(HttpServletRequest request){
        System.out.println("Login Request received");
        Map<String, String> response = new HashMap<>();
        response.put("sessionId",request.getSession().getId());
        response.put("CSRFtoken", ((CsrfToken)request.getAttribute("_csrf")).getToken()
        );
        return  response;
    }
}
