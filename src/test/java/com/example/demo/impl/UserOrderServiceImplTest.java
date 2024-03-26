package com.example.demo.impl;

import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.impl.UserOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserOrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UserOrderServiceImpl orderService;
    private User user;
    private UserOrder userOrder;

    @BeforeEach
    public void setup() {
        Item item = Item.builder().name("Computer")
                .price(BigDecimal.valueOf(23))
                .description("Good performance")
                .build();

        Cart cart = Cart.builder()
                .id(1L)
                .items(List.of(item))
                .total(BigDecimal.valueOf(234))
                .build();

        user = User.builder().username("wilki").build();
        user.setCart(cart);
        userOrder = UserOrder.createFromCart(user.getCart());
    }

    @Test
    void submitTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(orderRepository.save(any(UserOrder.class))).thenReturn(userOrder);
        UserOrder order1 = orderService.submit(user.getUsername());

        Assertions.assertEquals(BigDecimal.valueOf(234), order1.getTotal());

    }

    @Test
    void getOrdersForUserTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(orderRepository.findByUser(user)).thenReturn(List.of(userOrder));
        List<UserOrder> userOrders = orderService.getOrdersForUser(user.getUsername());
        Assertions.assertEquals(1, userOrders.size());
    }
}
