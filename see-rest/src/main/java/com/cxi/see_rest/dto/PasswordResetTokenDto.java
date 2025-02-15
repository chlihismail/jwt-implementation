package com.cxi.see_rest.dto;

import java.util.Arrays;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PasswordResetTokenDto(

    @NotBlank(message = "Token must not be blank")
    String token,

    @Size(min = 8, max = 50)
    char[] newPassword  
){
    @Override
    public String toString() {
        return "PasswordResetTokenDto(token=" + token + ", newPassword=HIDDEN)";
    }
    
    public void erasePassword() {
        Arrays.fill(newPassword, ' '); // Overwrite password memory
    }
}
