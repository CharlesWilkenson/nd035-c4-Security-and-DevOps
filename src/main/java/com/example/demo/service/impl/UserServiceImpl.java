package com.example.demo.service.impl;

import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.exception.ResourceExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Role;
import com.example.demo.model.RoleEnum;
import com.example.demo.model.User;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final CartRepository cartRepository;

    Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(rollbackOn = {ResourceExistsException.class, ResourceNotFoundException.class})
    public User signup(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .cart(new Cart())
                .build();

        Set<String> strRoles = signupRequest.getRoleEnum().stream().map(Enum::name).collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>();

        if (userRepository.existsByUsername(user.getUsername()))
            throw new ResourceExistsException("Error: Username is already taken!");

        if (userRepository.existsByEmail(user.getEmail()))
            throw new ResourceExistsException("Error: Email is already in use!");

        strRoles.forEach(role -> {
            if (role.equals("admin")) {
                Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(RoleEnum.USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                roles.add(userRole);
            }
        });

        user.setRoles(roles);
        User savedUser = null;
        try {
            savedUser = userRepository.save(user);
            Cart cart = new Cart();
            cart.setUser(savedUser);
            cartRepository.save(cart);
        }catch (Exception e){
            logger.info("Signup:signup process failed");
        }

        return savedUser;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
