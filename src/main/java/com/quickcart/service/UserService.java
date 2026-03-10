package com.quickcart.service;

import com.quickcart.model.User;
import com.quickcart.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.quickcart.dto.UserDTOs.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository= userRepository;
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
                .map(p -> "Login Successful.")
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }
}
