package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.User;
import com.springsec.studenttracker.repository.UserRepository;
import org.springframework.security.core.Authentication;

public abstract class BaseController {

    protected final UserRepository userRepository;

    public BaseController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}