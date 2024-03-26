package com.example.demo.impl;

import com.example.demo.dto.requests.ModifyCartRequest;
import com.example.demo.model.*;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private CartServiceImpl cartService;
    private Cart cart;
    private User user;
    private Item item1;
    private Item item2;
    private ModifyCartRequest request;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        Role role1 = Role.builder().name(RoleEnum.ADMIN).build();
        Role role2 = Role.builder().name(RoleEnum.USER).build();

        item1 = Item.builder().name("Computer")
                .price(BigDecimal.valueOf(2500))
                .description("Good performance")
                .build();

        item2 = Item.builder().name("Car")
                .price(BigDecimal.valueOf(250000))
                .description("It is the fastest car")
                .build();

        user = User.builder()
                .id(1L)
                .username("wilki")
                .password("charles")
                .email("ramesh@gmail.com")
                .cart(Cart.builder().id(1L).build())
                .roles(Set.of(role1, role2))
                .build();

        cart = Cart.builder()
                .id(1L)
               // .items(Collections.emptyList())
                .total(BigDecimal.valueOf(2500))
                .build();
         //  cart.addItem(item1);
     //   cart.addItem(item2);

        request = ModifyCartRequest.builder()
                .username("wilki")
                .itemId(1)
                .quantity(1)
                .build();
    }

    @Test
    void addToCartTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));
        cart.addItem(item1);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart cart1 = cartService.addToCart(request);
        Assertions.assertEquals(1, cart1.getItems().size());
    }

    @Test
    void removeFromCart() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));

        cart.addItem(item1);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cart.removeItem(item1);
        Cart removed = cartService.removeFromCart(request);
        Assertions.assertEquals(0, removed.getItems().size());
    }
}
