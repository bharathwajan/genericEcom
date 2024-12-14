package com.generic.ecom.view.security;

import com.generic.ecom.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrinciple implements UserDetails {
    /*
    userPrinciple Aka Principle user ,Principle User is the one who is currently trying to login
    into the system this class is used to get info about that guy/girl
    */

    @Autowired
    private Users user;
    public UserPrinciple(Users user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return  user.getUserName();
    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
