package com.CPAN228.distribution_centre_manager.service;

import com.CPAN228.distribution_centre_manager.model.Item;
import com.CPAN228.distribution_centre_manager.data.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItemsByBrandAndName(String brand, String name) {
        return itemRepository.findByBrandAndName(brand, name);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
