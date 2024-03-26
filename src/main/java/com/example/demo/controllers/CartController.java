package com.example.demo.controllers;

import com.example.demo.dto.requests.ModifyCartRequest;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    Logger logger = LogManager.getLogger(CartController.class);
    private final CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        logger.info("Cart:add item to cart process started");
        return ResponseEntity.ok(cartService.addToCart(request));
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        logger.info("Cart:remove item to cart process started");
        return ResponseEntity.ok(cartService.removeFromCart(request));
    }
}
