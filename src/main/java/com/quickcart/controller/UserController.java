package com.quickcart.controller;

import com.quickcart.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import com.quickcart.dto.UserDTOs.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user registration and authentication")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Creates a user account and returns the profile without the password.")
    public UserProfileRes register(@Valid @RequestBody RegisterReq req) {
        return userService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Validates credentials")
    public String login(@Valid @RequestBody LoginReq req) {
        return userService.login(req);
    }
}
