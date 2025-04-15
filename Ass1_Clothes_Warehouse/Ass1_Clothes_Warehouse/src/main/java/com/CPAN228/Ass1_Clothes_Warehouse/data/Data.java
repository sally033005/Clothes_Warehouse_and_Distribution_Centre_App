package com.CPAN228.Ass1_Clothes_Warehouse.data;

import com.CPAN228.Ass1_Clothes_Warehouse.model.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Data implements CommandLineRunner {

    private final ItemRepository itemRepository;

    public Data(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        itemRepository.save(new Item("T-shirt", "BALENCIAGA", 2022, 1500));
        itemRepository.save(new Item("Jacket", "STONE_ISLAND", 2022, 2500));
        itemRepository.save(new Item("Jeans", "DIOR", 2022, 2000));
    }
}
