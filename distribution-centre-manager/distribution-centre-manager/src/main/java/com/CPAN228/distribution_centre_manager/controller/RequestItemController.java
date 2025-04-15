package com.CPAN228.distribution_centre_manager.controller;

import com.CPAN228.distribution_centre_manager.data.DistributionCentreRepository;
import com.CPAN228.distribution_centre_manager.model.DistributionCentre;
import com.CPAN228.distribution_centre_manager.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RequestItemController {

    @Autowired
    private DistributionCentreRepository distributionCentreRepository;

    @GetMapping("/request-item")
    public String showRequestForm(Model model) {
        // Get all distribution centres
        List<DistributionCentre> centres = distributionCentreRepository.findAll();
        model.addAttribute("distributionCentres", centres);

        // Get all unique items across all centres
        Set<Item> uniqueItems = new HashSet<>();
        for (DistributionCentre centre : centres) {
            uniqueItems.addAll(centre.getItemsAvailable());
        }
        model.addAttribute("items", new ArrayList<>(uniqueItems));

        return "request-item-form";
    }

    @PostMapping("/request-item")
    public String processRequest(@RequestParam Long distributionCentreId,
                               @RequestParam String itemName,
                               @RequestParam String brand,
                               @RequestParam int quantity,
                               RedirectAttributes redirectAttributes) {
        try {
            // Find the distribution centre
            DistributionCentre centre = distributionCentreRepository.findById(distributionCentreId)
                    .orElseThrow(() -> new RuntimeException("Distribution centre not found"));

            // Find the item in the centre
            Item requestedItem = null;
            for (Item item : centre.getItemsAvailable()) {
                if (item.getName().equals(itemName) && item.getBrand().equals(brand)) {
                    requestedItem = item;
                    break;
                }
            }

            if (requestedItem == null) {
                redirectAttributes.addFlashAttribute("error", "Item not found in the selected distribution centre");
                return "redirect:/request-item";
            }

            // Check if enough quantity is available
            if (requestedItem.getQuantity() < quantity) {
                redirectAttributes.addFlashAttribute("error", 
                    "Not enough quantity available. Only " + requestedItem.getQuantity() + " units in stock.");
                return "redirect:/request-item";
            }

            // Update the quantity
            requestedItem.setQuantity(requestedItem.getQuantity() - quantity);
            
            // Save the changes
            distributionCentreRepository.save(centre);

            redirectAttributes.addFlashAttribute("success", 
                "Successfully requested " + quantity + " units of " + itemName + " (" + brand + ") from " + centre.getName());
            return "redirect:/distribution-centres";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred: " + e.getMessage());
            return "redirect:/request-item";
        }
    }
} 