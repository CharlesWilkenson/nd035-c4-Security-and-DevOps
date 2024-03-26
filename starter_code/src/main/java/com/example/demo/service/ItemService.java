package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);

    List<Item> getItems();

    Item getItemById(Long id);

    List<Item> getItemsByName(String name);
}
