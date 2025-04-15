package com.CPAN228.distribution_centre_manager.data;

import com.CPAN228.distribution_centre_manager.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBrandAndName(String brand, String name);
}
