package com.example.demo.impl;

import com.example.demo.model.Item;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;

    private Item item;

    @BeforeEach
    public void setup() {
        item = Item.builder().name("Computer")
                .price(BigDecimal.valueOf(2500))
                .description("Good performance")
                .build();
    }

    @DisplayName("JUnit test for save item")
    @Test
    public void givenItemObject_whenSaveItem_thenReturnItemObject() {
        when(itemRepository.save(item)).thenReturn(item);
        Item item1 = itemService.save(item);

        Assertions.assertNotNull(item1);
        Assertions.assertEquals("Computer", item1.getName());
        Assertions.assertEquals("Good performance", item1.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(2500), item1.getPrice());
    }

    @DisplayName("JUnit test for find item by Id")
    @Test
    public void whenFindItemById_thenReturnItemObject() {

        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));
        Item item1 = itemService.getItemById(1L);

        Assertions.assertNotNull(item1);
        Assertions.assertEquals("Computer", item1.getName());
        Assertions.assertEquals("Good performance", item1.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(2500), item1.getPrice());
    }

    @DisplayName("JUnit test get all items")
    @Test
    public void whenFindAllItems_thenReturnAllItemsObject() {
        Item item2 = Item.builder().name("Car")
                .price(BigDecimal.valueOf(250000))
                .description("It is the fastest car")
                .build();
        when(itemRepository.findAll()).thenReturn(List.of(item, item2));
        List<Item> itemList = itemService.getItems();

        Assertions.assertNotNull(itemList);
        Assertions.assertEquals(2, itemList.size());
    }

    @DisplayName("JUnit test for find item by Id")
    @Test
    public void whenFindAllItemsByName_thenReturnItemsObject() {
        Item item2 = Item.builder().name("Car")
                .price(BigDecimal.valueOf(250000))
                .description("It is the fastest car")
                .build();
        when(itemRepository.findByName("Car")).thenReturn(List.of(item2));
        List<Item> itemList = itemService.getItemsByName("Car");

        Assertions.assertNotNull(itemList);
        Assertions.assertEquals(1, itemList.size());
    }
}
