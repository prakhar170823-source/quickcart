package com.quickcart.service;

import com.quickcart.model.User;
import com.quickcart.repository.UserRepository;
import com.quickcart.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quickcart.dto.UserDTOs.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository= userRepository;
        this.jwtService= jwtService;
        this.passwordEncoder= passwordEncoder;
    }

    public UserProfileRes register(RegisterReq req) {

        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPassword(passwordEncoder.encode(req.password()));

        User savedUser = userRepository.save(user);

        return new UserProfileRes(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public String login(LoginReq req) {
        return userRepository.findByEmail(req.email())
                .filter(p -> passwordEncoder.matches(req.password(), p.getPassword()))
                .map(p -> jwtService.generateToken(p.getEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
}
