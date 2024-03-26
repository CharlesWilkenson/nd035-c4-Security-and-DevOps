package com.example.demo.controllers;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    Logger logger = LogManager.getLogger(ItemController.class);
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        logger.info("Item:get all items process started");
        List<Item> items = itemService.getItems();
        return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        logger.info("Item:get item by id process started");
        Item item = itemService.getItemById(id);
        return item == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(item);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
        logger.info("Item:get item by name process started");
        List<Item> items = itemService.getItemsByName(name);
        return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(items);

    }

}
