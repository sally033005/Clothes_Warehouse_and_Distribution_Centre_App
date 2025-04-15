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
        // Create Toronto Distribution Centre
        DistributionCentre toronto = new DistributionCentre();
        toronto.setName("Toronto DC");
        toronto.setLatitude(43.6532);
        toronto.setLongitude(-79.3832);
        
        List<Item> torontoItems = new ArrayList<>();
        
        Item item1 = new Item();
        item1.setName("T-Shirt");
        item1.setBrand("Nike");
        item1.setQuantity(100);
        torontoItems.add(item1);
        
        Item item2 = new Item();
        item2.setName("Jeans");
        item2.setBrand("Levi's");
        item2.setQuantity(50);
        torontoItems.add(item2);
        
        toronto.setItemsAvailable(torontoItems);
        distributionCentreRepository.save(toronto);

        // Create Vancouver Distribution Centre
        DistributionCentre vancouver = new DistributionCentre();
        vancouver.setName("Vancouver DC");
        vancouver.setLatitude(49.2827);
        vancouver.setLongitude(-123.1207);
        
        List<Item> vancouverItems = new ArrayList<>();
        
        Item item3 = new Item();
        item3.setName("Hoodie");
        item3.setBrand("Adidas");
        item3.setQuantity(75);
        vancouverItems.add(item3);
        
        Item item4 = new Item();
        item4.setName("Sneakers");
        item4.setBrand("Puma");
        item4.setQuantity(30);
        vancouverItems.add(item4);
        
        vancouver.setItemsAvailable(vancouverItems);
        distributionCentreRepository.save(vancouver);

        // Create Montreal Distribution Centre
        DistributionCentre montreal = new DistributionCentre();
        montreal.setName("Montreal DC");
        montreal.setLatitude(45.5017);
        montreal.setLongitude(-73.5673);
        
        List<Item> montrealItems = new ArrayList<>();
        
        Item item5 = new Item();
        item5.setName("T-Shirt");
        item5.setBrand("Nike");
        item5.setQuantity(100);
        montrealItems.add(item5);
        
        Item item6 = new Item();
        item6.setName("Socks");
        item6.setBrand("Under Armour");
        item6.setQuantity(200);
        montrealItems.add(item6);
        
        montreal.setItemsAvailable(montrealItems);
        distributionCentreRepository.save(montreal);

        // Create Calgary Distribution Centre
        DistributionCentre calgary = new DistributionCentre();
        calgary.setName("Calgary DC");
        calgary.setLatitude(51.0447);
        calgary.setLongitude(-114.0719);
        
        List<Item> calgaryItems = new ArrayList<>();
        
        Item item7 = new Item();
        item7.setName("Jacket");
        item7.setBrand("The North Face");
        item7.setQuantity(40);
        calgaryItems.add(item7);
        
        Item item8 = new Item();
        item8.setName("Boots");
        item8.setBrand("Timberland");
        item8.setQuantity(60);
        calgaryItems.add(item8);
        
        calgary.setItemsAvailable(calgaryItems);
        distributionCentreRepository.save(calgary);

        // Create Ottawa Distribution Centre
        DistributionCentre ottawa = new DistributionCentre();
        ottawa.setName("Ottawa DC");
        ottawa.setLatitude(45.4215);
        ottawa.setLongitude(-75.6972);
        
        List<Item> ottawaItems = new ArrayList<>();
        
        Item item9 = new Item();
        item9.setName("Sweater");
        item9.setBrand("Ralph Lauren");
        item9.setQuantity(45);
        ottawaItems.add(item9);
        
        Item item10 = new Item();
        item10.setName("Hat");
        item10.setBrand("New Era");
        item10.setQuantity(80);
        ottawaItems.add(item10);
        
        ottawa.setItemsAvailable(ottawaItems);
        distributionCentreRepository.save(ottawa);
    }
} 