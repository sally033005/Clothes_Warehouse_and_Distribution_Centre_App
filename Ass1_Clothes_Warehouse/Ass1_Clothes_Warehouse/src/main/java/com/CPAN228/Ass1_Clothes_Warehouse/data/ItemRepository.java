package com.CPAN228.Ass1_Clothes_Warehouse.data;

import com.CPAN228.Ass1_Clothes_Warehouse.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.brand = ?1 AND i.year = 2022")
    List<Item> findByBrandAndYear2022(String brand);

    // New: for dropdown in request form
    @Query("SELECT DISTINCT i.brand FROM Item i")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT i.name FROM Item i")
    List<String> findDistinctNames();

    Page<Item> findAll(Pageable pageable);
}
