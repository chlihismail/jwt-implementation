package com.cxi.see_rest.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.see_rest.dto.AppUserDto;
import com.cxi.see_rest.dto.CredentialsDto;
import com.cxi.see_rest.dto.SignUpDto;
import com.cxi.see_rest.security.AppUserAuthenticationProvider;
import com.cxi.see_rest.service.AppUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class AuthController{

    private final AppUserService appUserService;
    private final AppUserAuthenticationProvider appUserAuthenticationProvider;

    public AuthController(AppUserService appUserService, AppUserAuthenticationProvider appUserAuthenticationProvider) {
        this.appUserService = appUserService;
        this.appUserAuthenticationProvider = appUserAuthenticationProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AppUserDto> login(@RequestBody @Valid CredentialsDto credentialsDto){
        AppUserDto appUserDto = appUserService.login(credentialsDto);
        appUserDto = new AppUserDto.Builder()
        .id(appUserDto.id())
        .firstname(appUserDto.firstname())
        .lastname(appUserDto.lastname())
        .email(appUserDto.email())
        .role(appUserDto.role())
        .token(appUserAuthenticationProvider.createToken(appUserDto.email())).build();
        return ResponseEntity.ok(appUserDto);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> register(@RequestBody @Valid SignUpDto signUpDto){
        AppUserDto createdAppUser = appUserService.register(signUpDto);
        createdAppUser = new AppUserDto.Builder()
            .id(createdAppUser.id())
            .firstname(createdAppUser.firstname())
            .lastname(createdAppUser.lastname())
            .email(createdAppUser.email())
            .role(createdAppUser.role())
            .token(appUserAuthenticationProvider.createToken(signUpDto.email()))
            .build();
        return ResponseEntity.created(URI.create("/api/users/"+createdAppUser.id())).body(createdAppUser);
    }

}
