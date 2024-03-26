package com.example.demo.controllers;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK //
        , classes = {ItemController.class})
public class ItemControllerTest {
    private ItemController itemController;
    @MockBean
    private ItemService itemService;
    private Item item;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.itemController = new ItemController(itemService);

        item = Item.builder()
                .id(1L)
                .name("Computer")
                .price(BigDecimal.valueOf(2500))
                .description("Good performance")
                .build();
    }

    @Test
    void getItems() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(itemService.getItems()).thenReturn(List.of(item));
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetItemById() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(itemService.getItemById(anyLong())).thenReturn(item);
        ResponseEntity<Item> response = itemController.getItemById(item.getId());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("Computer", Objects.requireNonNull(response.getBody()).getName());
        assertEquals("Good performance", Objects.requireNonNull(response.getBody()).getDescription());
    }

    @Test
    void testGetItemsByName() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(itemService.getItemsByName(anyString())).thenReturn(List.of(item));
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Computer");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(BigDecimal.valueOf(2500), Objects.requireNonNull(Objects.requireNonNull(response.getBody()).get(0)).getPrice());
        assertEquals("Good performance", Objects.requireNonNull(response.getBody().get(0)).getDescription());
    }
}
