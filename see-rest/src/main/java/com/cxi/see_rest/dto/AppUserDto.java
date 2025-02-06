package com.cxi.see_rest.dto;

import com.cxi.see_rest.enums.AppUserRole;

public record AppUserDto(
    String id,
    String firstname,
    String lastname,
    String email,
    AppUserRole role,
    String token
){
    public static class Builder{
        private String id;
        private String firstname;
        private String lastname;
        private String email;
        private AppUserRole role;
        private String token;

        public Builder id(String id){
            this.id=id;
            return this;
        }

        public Builder firstname(String firstname){
            this.firstname=firstname;
            return this;
        }

        public Builder lastname(String lastname){
            this.lastname=lastname;
            return this;
        }

        public Builder email(String email){
            this.email=email;
            return this;
        } 

        public Builder role(AppUserRole role){
            this.role=role;
            return this;
        } 

        public Builder token(String token){
            this.token=token;
            return this;
        }

        public AppUserDto build(){
            return new AppUserDto(id, firstname, lastname, email, role, token);
        }
    }
}
