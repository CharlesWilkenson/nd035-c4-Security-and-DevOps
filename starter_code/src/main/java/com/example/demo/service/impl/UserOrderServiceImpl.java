package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements OrderService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    Logger logger = LogManager.getLogger(UserOrderServiceImpl.class);
    @Override
    @Transactional(rollbackOn = ResourceNotFoundException.class)
    public UserOrder submit(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserOrder userOrder = UserOrder.createFromCart(user.getCart());
        UserOrder savedOrder = null;
        try {
            savedOrder = orderRepository.save(userOrder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Order:order process failed");
        }

        return savedOrder;
    }

    @Override
    public List<UserOrder> getOrdersForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findByUser(user);
    }
}
