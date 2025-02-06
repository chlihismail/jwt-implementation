package com.cxi.see_rest.dto;

public record CredentialsDto(
    String email,
    char[] password
){}
