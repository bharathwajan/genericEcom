package com.generic.ecom.view.security.customFilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class IdempotencyFilter extends OncePerRequestFilter {

    @Value("${spring.cache.type}")
    private String redisEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getRequestURI().startsWith("/putProducts") || redisEnabled.equalsIgnoreCase("none")) {
            filterChain.doFilter(request, response);
            return;
        }

        String idempotencyKey = request.getHeader("Idempotency-Key");
        // Check if the Idempotency-Key header is present --> if not, return 400 Bad Request else serve the request // will implement after interview
    }
}
