package com.CPAN228.Ass1_Clothes_Warehouse.controller;

import com.CPAN228.Ass1_Clothes_Warehouse.data.ItemRepository;
import com.CPAN228.Ass1_Clothes_Warehouse.model.Item;
import com.CPAN228.Ass1_Clothes_Warehouse.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ItemRepository itemRepository;

    public HomeController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // Dashboard Data
        int lowStockThreshold = 10;
        long lowStockCount = itemRepository.countByQuantityLessThanEqual(lowStockThreshold);
        List<Item> lowStockItems = itemRepository.findByQuantityLessThanEqual(lowStockThreshold);

        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("lowStockItems", lowStockItems);

        // Mock Data for Dashboard
        model.addAttribute("mockAwaitingApproval", 3);
        model.addAttribute("mockReadyForShipment", 6);
        model.addAttribute("recentActivities", List.of(
                "10:42 AM - Bobby added 60 Nike T-shirts (+12% stock)",
                "9:30 AM - Jennifer edited the quantity of Nike pants (critical -> safe)",
                "8:45 AM - Bobby added 50 Under Armour Socks",
                "Yesterday - Jennifer added 80 Adidas Sweaters",
                "Yesterday - Bobby added 70 Puma hoodies"));

        return "home";
    }
}