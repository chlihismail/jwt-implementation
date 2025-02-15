package com.cxi.see_rest.dto;

import java.util.Arrays;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CredentialsDto(
    @Email
    @NotBlank(message = "Email must not be blank")
    String email,

    @NotEmpty
    @Size(min = 8, max = 50)
    char[] password,
    
    boolean rememberMe
){
    @Override
    public String toString(){
        return "CredentialDto(HIDDEN_INFO)";
    }
}
