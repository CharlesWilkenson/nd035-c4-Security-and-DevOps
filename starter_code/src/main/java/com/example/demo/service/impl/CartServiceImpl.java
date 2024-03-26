package com.example.demo.service.impl;

import com.example.demo.dto.requests.ModifyCartRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final ItemRepository itemRepository;

    @Override
    @Transactional(rollbackOn = ResourceNotFoundException.class)
    public Cart addToCart(ModifyCartRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        Cart cart = user.getCart() == null ? new Cart() : user.getCart();

        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item));

        return cartRepository.save(cart);
    }

    @Override
    @Transactional(rollbackOn = ResourceNotFoundException.class)
    public Cart removeFromCart(ModifyCartRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        Cart cart = user.getCart() == null ? new Cart() : user.getCart();

        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item));

        return cartRepository.save(cart);
    }

}
