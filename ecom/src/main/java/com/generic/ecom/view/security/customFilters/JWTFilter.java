package com.generic.ecom.view.security.customFilters;

import com.generic.ecom.view.security.ecomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import  com.generic.ecom.view.security.JWTService;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JWTFilter extends OncePerRequestFilter {
    /*
    Purpose of this filter is to intercept the incoming requests and check if the request has a valid JWT token.
     */

    @Autowired
    private JWTService JwtService; // service to handle the jwt token operations

    @Autowired
    private ecomUserDetailsService userDetailsService; // service to load user details

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaGFyYXRod2FqYW5yIiwiaWF0IjoxNzUwNTM4ODU1LCJleHAiOjE3NTA1NDYwNTV9.rfNsR75vempSnYX_pRu-rsL5bm2QW2Zqv9fn-np0jxw
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken =null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            userName = JwtService.extractUserName(jwtToken);
        }
        if(userName!= null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
            if(JwtService.validateToken(jwtToken, userDetails)){
                // if the token is valid then we will set the authentication in the security context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
