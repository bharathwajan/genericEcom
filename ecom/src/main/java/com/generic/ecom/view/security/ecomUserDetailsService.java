package com.generic.ecom.view.security;

import com.generic.ecom.model.Users;
import com.generic.ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ecomUserDetailsService implements UserDetailsService {
    /*
    The UserDetailsService interface is used to load user-specific data during the authentication process.
     It is a core component for managing user details in Spring Security, such as retrieving user information
     (username, password, roles, etc.) from a database or any other external source.
     */
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        the purpose of this fuction is to get the user data and return it to auth provider so that auth provider can do its job
        this method return UserDetails object --> Since UserDetails is a object we need to create a class for it. which is what we did in
        UserPrinciple.java.
         */

        Users user=userRepo.getUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException(username+"not found");
        }
        // else you need to return UserDetails
        return new UserPrinciple(user);
    }
}
