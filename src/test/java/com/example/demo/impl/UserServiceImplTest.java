package com.example.demo.impl;

import com.example.demo.dto.requests.SignupRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.Role;
import com.example.demo.model.RoleEnum;
import com.example.demo.model.User;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private  CartRepository cartRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  RoleRepository roleRepository;
    @Mock
    private  PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    private SignupRequest signupRequest;
    private User user;
    List<Role> roles = new ArrayList<>();
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
                .cart(new Cart())
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .roles( Sets.newHashSet(roles))
                .build();

        roleRepository.saveAll(roles);
    }

    @DisplayName("JUnit test for signup method")
    @Test
    public void givenUserObject_whenSaveUser_thenReturnUserObject_(){
        when(roleRepository.findByName(any(RoleEnum.class))).thenReturn(Optional.ofNullable(Role.builder().name(RoleEnum.ADMIN).build()));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User user1 = userService.signup(signupRequest);

        Assertions.assertEquals("wilki", user1.getUsername());
        Assertions.assertEquals("ramesh@gmail.com", user1.getEmail());
    }

    @DisplayName("JUnit test for finding user by id method")
    @Test
    public void whenFindUserById_thenReturnUserObject(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User user1 = userService.findById(1L);

        Assertions.assertEquals("wilki", user1.getUsername());
        Assertions.assertEquals("ramesh@gmail.com", user1.getEmail());
    }

    @DisplayName("JUnit test for signup method")
    @Test
    public void whenFindUserByName_thenReturnUserObject(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        User user1 = userService.findByUsername("wilki");

        Assertions.assertEquals("wilki", user1.getUsername());
        Assertions.assertEquals("ramesh@gmail.com", user1.getEmail());
    }


}
