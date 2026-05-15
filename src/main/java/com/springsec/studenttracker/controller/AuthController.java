package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.*;
import com.springsec.studenttracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // MANUAL CONSTRUCTOR
    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        return "User registered successfully";
    }
}