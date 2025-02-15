package com.cxi.see_rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmailDto(
    @NotEmpty
    @Email(message = "Invalid email format")
    String email
)
{
}
