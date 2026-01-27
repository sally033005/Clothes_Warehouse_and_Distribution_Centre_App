package com.CPAN228.Ass1_Clothes_Warehouse.data;

import com.CPAN228.Ass1_Clothes_Warehouse.model.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Data implements CommandLineRunner {

    private final ItemRepository itemRepository;

    public Data(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Step 1: Surgical Deduplication for known initial data
        // This cleans up any existing duplicates while preserving other data
        List<Item> allItems = itemRepository.findAll();
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (Item item : allItems) {
            String key = item.getName() + "|" + item.getBrand() + "|" + item.getYear();
            if (seen.contains(key)) {
                itemRepository.delete(item);
            } else {
                seen.add(key);
            }
        }

        // Step 2: Seed initial items if they don't exist
        saveIfNotExist(new Item("T-shirt", "BALENCIAGA", 2022, 1500));
        saveIfNotExist(new Item("Sweater", "BALENCIAGA", 2025, 2100));
        saveIfNotExist(new Item("Hoodie", "BALENCIAGA", 2024, 2200));
        saveIfNotExist(new Item("Skirt", "BALENCIAGA", 2025, 1200));
        saveIfNotExist(new Item("Jacket", "STONE_ISLAND", 2022, 2500));
        saveIfNotExist(new Item("Shorts", "STONE_ISLAND", 2024, 1600));
        saveIfNotExist(new Item("Jeans", "STONE_ISLAND", 2025, 2600));
        saveIfNotExist(new Item("Dress", "STONE_ISLAND", 2024, 1600));
        saveIfNotExist(new Item("Jacket", "DIOR", 2024, 2700));
        saveIfNotExist(new Item("Jeans", "DIOR", 2022, 2000));
        saveIfNotExist(new Item("T-shirt", "DIOR", 2025, 1200));
        saveIfNotExist(new Item("Sweater", "DIOR", 2024, 1800));
    }

    private void saveIfNotExist(Item item) {
        if (!itemRepository.existsByNameAndBrandAndYear(item.getName(), item.getBrand(), item.getYear())) {
            itemRepository.save(item);
        }
    }
}
