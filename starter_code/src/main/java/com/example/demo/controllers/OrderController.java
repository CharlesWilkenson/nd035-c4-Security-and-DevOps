package com.example.demo.controllers;

import com.example.demo.model.UserOrder;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    Logger logger = LogManager.getLogger(OrderController.class);
    private final OrderService orderService;

    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username) {
        logger.info("Order:submit order process started");
        return ResponseEntity.ok(orderService.submit(username));
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        logger.info("Order:find orders by username process started");
        return ResponseEntity.ok(orderService.getOrdersForUser(username));
    }
}