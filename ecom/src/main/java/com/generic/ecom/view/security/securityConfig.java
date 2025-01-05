package com.generic.ecom.view.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// think this file as a config file

@Configuration
@EnableWebSecurity
public class securityConfig {

    @Autowired
    private ecomUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        Bean to overide the default filter chain.
         */
        http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/signIn","/healthCheck")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        /*
        This AuthenticationProvider is the spring's defualt provider since we want implement our AuthenticationProvider
        we are replacing that bean with our bean.

        In application context this bean will be given priority

        The AuthenticationProvider validates the authentication request (typically an Authentication object) and
         returns an authenticated Authentication object or throws an exception if the authentication fails.
         */
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(5));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        return new WebSecurityCustomizer() {    --> this is calles ananomous class
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().requestMatchers("/signIn");
//            }
//        };
        return (webSecCust) -> webSecCust.ignoring().requestMatchers("/signIn","/healthCheck");  // this lambda implementation simplyfies the above lines
        // (web) create the class and the method inside it with @override and the contents after  --> will be pasted inside the method.
    }
}