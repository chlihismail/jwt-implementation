package com.cxi.see_rest.controller;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxi.see_rest.dto.AppUserDto;
import com.cxi.see_rest.dto.CredentialsDto;
import com.cxi.see_rest.dto.EmailDto;
import com.cxi.see_rest.dto.PasswordResetTokenDto;
import com.cxi.see_rest.dto.SignUpDto;
import com.cxi.see_rest.security.AppUserAuthenticationProvider;
import com.cxi.see_rest.service.AppUserService;
import com.cxi.see_rest.service.PasswordResetTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class AuthController{

    private final AppUserService appUserService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final AppUserAuthenticationProvider appUserAuthenticationProvider;

    public AuthController(AppUserService appUserService, PasswordResetTokenService passwordResetTokenService,
            AppUserAuthenticationProvider appUserAuthenticationProvider) {
        this.appUserService = appUserService;
        this.passwordResetTokenService = passwordResetTokenService;
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
        .token(appUserAuthenticationProvider.createToken(appUserDto.email(), credentialsDto.rememberMe())).build();
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
            .token(appUserAuthenticationProvider.createToken(signUpDto.email(), false))
            .build();
        return ResponseEntity.created(URI.create("/api/users/"+createdAppUser.id())).body(createdAppUser);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<Map<String, String>> forgetPassword(@RequestBody EmailDto emailDto){
        String email = emailDto.email();
        if(isValidEmailDomain(email)){
            passwordResetTokenService.sendPasswordResetEmail(email);
            return ResponseEntity.ok(Map.of("message", "Check your mail box!"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid domain!"));
        }
    } 

    private boolean isValidEmailDomain(String email){
        String[] emailParts = email.trim().split("@");
        if (emailParts.length != 2) return false;
        String domain = emailParts[1].trim();
        try {
            InetAddress.getByName(domain);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody @Valid PasswordResetTokenDto passwordResetTokenDto){
        passwordResetTokenService.resetPassword(passwordResetTokenDto.token(), passwordResetTokenDto.newPassword());
        return ResponseEntity.ok(Map.of("message", "Password reset successful!"));
    }

}
