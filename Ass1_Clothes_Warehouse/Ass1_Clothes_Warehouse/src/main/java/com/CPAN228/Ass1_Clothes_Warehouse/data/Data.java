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
        saveIfNotExist(new Item("Summer T-Shirt", "Nike", 2022, 1200, 15));
        saveIfNotExist(new Item("Performance Hoodie", "Adidas", 2023, 1500, 10));
        saveIfNotExist(new Item("Original Jeans", "Levi's", 2022, 1100, 20));
        saveIfNotExist(new Item("Winter Jacket", "The North Face", 2024, 2500, 5));
        saveIfNotExist(new Item("Running Shoes", "Puma", 2024, 1300, 12));
        saveIfNotExist(new Item("Training Shirt", "Under Armour", 2023, 1050, 18));
    }

    private void saveIfNotExist(Item item) {
        if (!itemRepository.existsByNameAndBrandAndYear(item.getName(), item.getBrand(), item.getYear())) {
            itemRepository.save(item);
        }
    }
}
