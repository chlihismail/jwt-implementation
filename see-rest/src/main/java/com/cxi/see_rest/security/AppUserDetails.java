package com.cxi.see_rest.security;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cxi.see_rest.model.AppUser;

public class AppUserDetails implements UserDetails{

    private final AppUser appUser; 

    public AppUserDetails(AppUser appUser){
        this.appUser=appUser;
    }
    
    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return appUser.getAccountExpiryDate() == null || appUser.getAccountExpiryDate().isAfter(Instant.now());
    }
    @Override
    public boolean isAccountNonLocked() {
        return !appUser.getLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return appUser.getPasswordExpiryDate() == null || appUser.getPasswordExpiryDate().isAfter(Instant.now());
    }
    @Override
    public boolean isEnabled() {
        return appUser.getEnabled();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name()));
    }

}
