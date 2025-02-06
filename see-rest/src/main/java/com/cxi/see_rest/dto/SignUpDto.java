package com.cxi.see_rest.dto;

import com.cxi.see_rest.enums.AppUserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SignUpDto(
    @NotEmpty
    @Size(min = 2, max = 50)
    String firstname,

    @NotEmpty
    @Size(min = 2, max = 50)
    String lastname,
    
    @NotEmpty
    @Email
    String email,

    @NotEmpty
    AppUserRole role,

    @NotEmpty
    @Size(min = 8, max = 100)
    char[] password
){
    public static class Builder{
        private String firstname;
        private String lastname;
        private String email;
        private AppUserRole role;
        private char[] password;

        public Builder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }
        
        public Builder role(AppUserRole role){
            this.role = role;
            return this;
        }

        public Builder password(char[] password){
            this.password = password;
            return this;
        }

        public SignUpDto build(){
            return new SignUpDto(firstname, lastname, email, role, password);
        }

    }
}
