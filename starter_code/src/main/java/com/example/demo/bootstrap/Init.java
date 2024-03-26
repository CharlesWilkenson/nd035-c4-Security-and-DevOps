package com.example.demo.bootstrap;

import com.example.demo.model.Item;
import com.example.demo.model.Role;
import com.example.demo.model.RoleEnum;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        List<Item> items = List.of(new Item(null,"Round Widget", BigDecimal.valueOf(2.99), "A widget that is round")
        ,new Item(null, "Square Widget", BigDecimal.valueOf(1.99), "A widget that is square"));
        itemRepository.saveAll(items);

        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().name(RoleEnum.ADMIN).build());
        roles.add(Role.builder().name(RoleEnum.USER).build());
        roleRepository.saveAll(roles);
    }
}
