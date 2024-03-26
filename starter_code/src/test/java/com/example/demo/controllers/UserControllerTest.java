package com.example.demo.controllers;

import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.Role;
import com.example.demo.model.RoleEnum;
import com.example.demo.model.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.service.UserService;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @InjectMocks
    private AuthController authController;
    @Mock
    private UserService userService;
    @Mock
    private RoleRepository roleRepository;
    private User user;
    List<Role> roles = new ArrayList<>();
    private SignupRequest signupRequest;
    @BeforeEach
    public void setup(){
        roles.add(Role.builder().name(RoleEnum.USER).build());
        roles.add(Role.builder().name(RoleEnum.ADMIN).build());

        signupRequest = SignupRequest.builder()
                .username("wilki")
                .password("charles")
                .email("ramesh@gmail.com")
                .roleEnum(Set.of(RoleEnum.ADMIN, RoleEnum.USER))
                .build();

        user = User.builder()
                .id(1L)
                .cart(new Cart())
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .roles( Sets.newHashSet(roles))
                .build();

        roleRepository.saveAll(roles);
    }

    @Test
    void testSignup() {
        when(userService.signup(any(SignupRequest.class))).thenReturn(user);
        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("User registered successfully!", Objects.requireNonNull(response.getBody()));
    }

    @Test
    void testFindById() {
        when(userService.findById(anyLong())).thenReturn(user);
        ResponseEntity<User> response = userController.findById(user.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("wilki", Objects.requireNonNull(response.getBody()).getUsername());

    }

    @Test
    void testFindByUserName() {
        when(userService.findByUsername(anyString())).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName("wilki");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals("ramesh@gmail.com", Objects.requireNonNull(response.getBody()).getEmail());
        Assertions.assertEquals("wilki", Objects.requireNonNull(response.getBody()).getUsername());
    }

}
