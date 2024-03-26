package com.example.demo.service;

import com.example.demo.dto.requests.ModifyCartRequest;
import com.example.demo.model.Cart;

public interface CartService {

    Cart addToCart(ModifyCartRequest request);

    Cart removeFromCart(ModifyCartRequest request);
}
