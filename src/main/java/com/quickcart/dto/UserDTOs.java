package com.quickcart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTOs {

    public record RegisterReq(
            @NotBlank(message = "Name is required")
            String name,
            @Email(message = "Invalid email format")
            @NotBlank
            String email,
            @NotBlank(message = "Password is required")
            String password
    ) {}

    public record LoginReq(
            @Email
            @NotBlank
            String email,
            @NotBlank
            String password
    ) {}

    public record UserProfileRes(
            Long id,
            String name,
            String email
    ) {}
}
