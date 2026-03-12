package com.quickcart.service;

import com.quickcart.model.User;
import com.quickcart.repository.UserRepository;
import com.quickcart.security.JwtService;
import org.springframework.stereotype.Service;
import com.quickcart.dto.UserDTOs.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository= userRepository;
        this.jwtService= jwtService;
    }

    public UserProfileRes register(RegisterReq req) {

        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPassword(req.password());

        User savedUser = userRepository.save(user);

        return new UserProfileRes(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public String login(LoginReq req) {
        return userRepository.findByEmail(req.email())
                .filter(p -> p.getPassword().equals(req.password()))
                .map(p -> jwtService.generateToken(p.getEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
}
