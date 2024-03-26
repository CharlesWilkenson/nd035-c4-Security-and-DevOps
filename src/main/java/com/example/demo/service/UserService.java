package com.example.demo.service;

import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User findByUsername(String username);

    User findById(Long id);

    User signup(SignupRequest signupRequest);
    UserDetailsService userDetailsService();
}
