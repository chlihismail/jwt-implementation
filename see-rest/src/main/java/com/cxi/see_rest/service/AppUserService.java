package com.cxi.see_rest.service;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cxi.see_rest.dto.AppUserDto;
import com.cxi.see_rest.dto.CredentialsDto;
import com.cxi.see_rest.dto.SignUpDto;
import com.cxi.see_rest.exception.AppException;
import com.cxi.see_rest.mapper.AppUserMapper;
import com.cxi.see_rest.model.AppUser;
import com.cxi.see_rest.repository.AppUserRepository;

@Service
public class AppUserService{

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder,
            AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserMapper = appUserMapper;
    }
    
    public AppUserDto login(CredentialsDto credentialsDto){
        AppUser appUser = appUserRepository.findByEmail(credentialsDto.email()).orElseThrow(()->new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), appUser.getPassword())){
            return appUserMapper.toAppUserDto(appUser);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public AppUserDto register(SignUpDto signUpDto){
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(signUpDto.email());

        if(optionalAppUser.isPresent()){
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = appUserMapper.toAppUser(signUpDto);
        appUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        AppUser savedAppUser = appUserRepository.save(appUser);

        return appUserMapper.toAppUserDto(savedAppUser);
    }

}
