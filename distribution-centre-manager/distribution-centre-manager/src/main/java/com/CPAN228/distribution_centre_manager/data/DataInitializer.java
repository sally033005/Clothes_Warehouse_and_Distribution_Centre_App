package com.CPAN228.distribution_centre_manager.data;

import com.CPAN228.distribution_centre_manager.model.DistributionCentre;
import com.CPAN228.distribution_centre_manager.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DistributionCentreRepository distributionCentreRepository;

    @Override
    public void run(String... args) {
        // Step 1: Surgical Deduplication for known initial centres
        // This cleans up any existing duplicates while preserving other data
        List<DistributionCentre> allCentres = distributionCentreRepository.findAll();
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (DistributionCentre centre : allCentres) {
            String key = centre.getName();
            if (seen.contains(key)) {
                distributionCentreRepository.delete(centre);
            } else {
                seen.add(key);
            }
        }

        // Step 2: Seed initial centres if they don't exist

        // Toronto
        if (!distributionCentreRepository.existsByName("Toronto DC")) {
            DistributionCentre toronto = new DistributionCentre();
            toronto.setName("Toronto DC");
            toronto.setLatitude(43.6532);
            toronto.setLongitude(-79.3832);

            List<Item> torontoItems = new ArrayList<>();
            torontoItems.add(createItem("Summer T-Shirt", "Nike", 500, 2022, 1200));
            torontoItems.add(createItem("Original Jeans", "Levi's", 300, 2022, 1100));

            toronto.setItemsAvailable(torontoItems);
            distributionCentreRepository.save(toronto);
        }

        // Vancouver
        if (!distributionCentreRepository.existsByName("Vancouver DC")) {
            DistributionCentre vancouver = new DistributionCentre();
            vancouver.setName("Vancouver DC");
            vancouver.setLatitude(49.2827);
            vancouver.setLongitude(-123.1207);

            List<Item> vancouverItems = new ArrayList<>();
            vancouverItems.add(createItem("Performance Hoodie", "Adidas", 400, 2023, 1500));
            vancouverItems.add(createItem("Running Shoes", "Puma", 250, 2024, 1300));

            vancouver.setItemsAvailable(vancouverItems);
            distributionCentreRepository.save(vancouver);
        }

        // Montreal
        if (!distributionCentreRepository.existsByName("Montreal DC")) {
            DistributionCentre montreal = new DistributionCentre();
            montreal.setName("Montreal DC");
            montreal.setLatitude(45.5017);
            montreal.setLongitude(-73.5673);

            List<Item> montrealItems = new ArrayList<>();
            montrealItems.add(createItem("Summer T-Shirt", "Nike", 600, 2022, 1200));
            montrealItems.add(createItem("Training Shirt", "Under Armour", 350, 2023, 1050));

            montreal.setItemsAvailable(montrealItems);
            distributionCentreRepository.save(montreal);
        }

        // Calgary
        if (!distributionCentreRepository.existsByName("Calgary DC")) {
            DistributionCentre calgary = new DistributionCentre();
            calgary.setName("Calgary DC");
            calgary.setLatitude(51.0447);
            calgary.setLongitude(-114.0719);

            List<Item> calgaryItems = new ArrayList<>();
            calgaryItems.add(createItem("Winter Jacket", "The North Face", 200, 2024, 2500));
            calgaryItems.add(createItem("Performance Hoodie", "Adidas", 300, 2023, 1500));

            calgary.setItemsAvailable(calgaryItems);
            distributionCentreRepository.save(calgary);
        }

        // Ottawa
        if (!distributionCentreRepository.existsByName("Ottawa DC")) {
            DistributionCentre ottawa = new DistributionCentre();
            ottawa.setName("Ottawa DC");
            ottawa.setLatitude(45.4215);
            ottawa.setLongitude(-75.6972);

            List<Item> ottawaItems = new ArrayList<>();
            ottawaItems.add(createItem("Original Jeans", "Levi's", 450, 2022, 1100));
            ottawaItems.add(createItem("Running Shoes", "Puma", 200, 2024, 1300));

            ottawa.setItemsAvailable(ottawaItems);
            distributionCentreRepository.save(ottawa);
        }
    }

    private Item createItem(String name, String brand, int quantity, int year, double price) {
        Item item = new Item();
        item.setName(name);
        item.setBrand(brand);
        item.setQuantity(quantity);
        item.setYear(year);
        item.setPrice(price);
        return item;
    }
}