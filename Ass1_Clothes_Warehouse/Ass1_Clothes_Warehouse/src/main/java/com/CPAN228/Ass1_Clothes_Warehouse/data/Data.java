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
        itemRepository.save(new Item("Sweater", "BALENCIAGA", 2025, 2100));
        itemRepository.save(new Item("Hoodie", "BALENCIAGA", 2024, 2200));
        itemRepository.save(new Item("Skirt", "BALENCIAGA", 2025, 1200));
        itemRepository.save(new Item("Jacket", "STONE_ISLAND", 2022, 2500));
        itemRepository.save(new Item("Shorts", "STONE_ISLAND", 2024, 1600));
        itemRepository.save(new Item("Jeans", "STONE_ISLAND", 2025, 2600));
        itemRepository.save(new Item("Dress", "STONE_ISLAND", 2024, 1600));
        itemRepository.save(new Item("Jacket", "DIOR", 2024, 2700));
        itemRepository.save(new Item("Jeans", "DIOR", 2022, 2000));
        itemRepository.save(new Item("T-shirt", "DIOR", 2025, 1200));
        itemRepository.save(new Item("Sweater", "DIOR", 2024, 1800));
    }
}
