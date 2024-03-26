package com.example.demo.controllers;

import com.example.demo.dto.requests.ModifyCartRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.service.CartService;
import com.example.demo.service.ItemService;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK //
        , classes = {ItemController.class})
public class CartControllerTest {

    private CartController cartController;

    @MockBean
    private CartService cartService;

    @MockBean
    private ItemService itemService;
    private Cart cart;
    private ModifyCartRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.cartController = new CartController(cartService);
        Item item1 = Item.builder()
                .id(1L)
                .name("Computer")
                .price(BigDecimal.valueOf(2500))
                .description("Good performance")
                .build();

        Item item2 = Item.builder().name("Car")
                .id(2L)
                .price(BigDecimal.valueOf(250000))
                .description("It is the fastest car")
                .build();

        cart = Cart.builder()
                .id(1L)
                .total(BigDecimal.valueOf(2500))
                .build();
        cart.addItem(item1);
        cart.addItem(item2);

        request = ModifyCartRequest.builder()
                .username("wilki")
                .itemId(1)
                .quantity(1)
                .build();
    }

    @Test
    void testAddToCart() {
        when(cartService.addToCart(request)).thenReturn(cart);
        ResponseEntity<Cart> response = cartController.addToCart(request);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Computer", Objects.requireNonNull(response.getBody()).getItems().get(0).getName());
        assertEquals("Good performance", Objects.requireNonNull(response.getBody()).getItems().get(0).getDescription());

    }

    @Test
    void testRemoveFromCart() {
        when(cartService.removeFromCart(request)).thenReturn(cart);
        ResponseEntity<Cart> response = cartController.addToCart(request);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }
}
