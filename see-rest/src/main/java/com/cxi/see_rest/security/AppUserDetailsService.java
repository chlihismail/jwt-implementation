package com.cxi.see_rest.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cxi.see_rest.exception.AppException;
import com.cxi.see_rest.model.AppUser;
import com.cxi.see_rest.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository){
        this.appUserRepository=appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(()->new AppException("Unknown user!", HttpStatus.NOT_FOUND));
        return new AppUserDetails(appUser);
    }
    
}
