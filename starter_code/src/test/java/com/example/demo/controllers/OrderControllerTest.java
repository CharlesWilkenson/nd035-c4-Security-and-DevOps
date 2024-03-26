package com.example.demo.controllers;

import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.service.ItemService;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK //
        , classes = {ItemController.class})
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    private OrderController orderController;
    @MockBean
    private ItemService itemService;
    private User user;
    private UserOrder userOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.orderController = new OrderController(orderService);

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
    void testSubmit() {
        when(orderService.submit(user.getUsername())).thenReturn(userOrder);
        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Computer", Objects.requireNonNull(response.getBody()).getItems().get(0).getName());
        assertEquals("Good performance", Objects.requireNonNull(response.getBody()).getItems().get(0).getDescription());
    }

    @Test
    void testGetOrdersForUser() {
        when(orderService.getOrdersForUser(user.getUsername())).thenReturn(List.of(userOrder));

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Computer", Objects.requireNonNull(Objects.requireNonNull(response.getBody()).get(0).getItems().get(0).getName()));
        assertEquals("Good performance", Objects.requireNonNull(Objects.requireNonNull(response.getBody()).get(0).getItems().get(0).getDescription()));

    }
}