package com.CPAN228.distribution_centre_manager.controller;

import com.CPAN228.distribution_centre_manager.model.DistributionCentre;
import com.CPAN228.distribution_centre_manager.model.Item;
import com.CPAN228.distribution_centre_manager.service.DistributionCentreService;
import com.CPAN228.distribution_centre_manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distribution-centres")
public class DistributionCentreController {

    @Autowired
    private DistributionCentreService distributionCentreService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<DistributionCentre> getAllDistributionCentres() {
        return distributionCentreService.getAllDistributionCentres();
    }

    @PostMapping("/add-centre")
    public DistributionCentre addCentre(@RequestBody DistributionCentre centre) {
        return distributionCentreService.saveCentre(centre);
    }    

    @PostMapping("/add-item")
    public ResponseEntity<?> addItemToDistributionCentre(@RequestBody Map<String, Object> request) {
        try {
            Long distributionCentreId = Long.parseLong(request.get("distributionCentreId").toString());
            String itemName = (String) request.get("name");
            String brand = (String) request.get("brand");
            int quantity = Integer.parseInt(request.get("quantity").toString());

            // Find the distribution centre
            DistributionCentre centre = distributionCentreService.getDistributionCentreById(distributionCentreId);
            if (centre == null) {
                return ResponseEntity.badRequest().body("Distribution centre not found");
            }

            // Create new item
            Item newItem = new Item();
            newItem.setName(itemName);
            newItem.setBrand(brand);
            newItem.setQuantity(quantity);

            // Add item to the centre's items list
            if (centre.getItemsAvailable() == null) {
                centre.setItemsAvailable(new ArrayList<>());
            }
            centre.getItemsAvailable().add(newItem);
            
            // Save the changes
            distributionCentreService.saveCentre(centre);

            return ResponseEntity.ok("Successfully added item to " + centre.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding item: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            // Find which distribution centre has this item
            List<DistributionCentre> centres = distributionCentreService.getAllDistributionCentres();
            DistributionCentre targetCentre = null;
            Item targetItem = null;

            // Find the item in any distribution centre
            for (DistributionCentre centre : centres) {
                for (Item item : centre.getItemsAvailable()) {
                    if (item.getId().equals(id)) {
                        targetCentre = centre;
                        targetItem = item;
                        break;
                    }
                }
                if (targetCentre != null) break;
            }

            if (targetCentre == null || targetItem == null) {
                return ResponseEntity.badRequest().body("Item not found in any distribution centre");
            }

            // Remove the item from the centre's list
            targetCentre.getItemsAvailable().remove(targetItem);
            distributionCentreService.saveCentre(targetCentre);

            // Delete the item from the repository
            itemService.deleteItem(id);

            return ResponseEntity.ok("Successfully deleted item from " + targetCentre.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting item: " + e.getMessage());
        }
    }

    @GetMapping("/request-item")
    public List<Item> requestItem(@RequestParam String brand, @RequestParam String name) {
        return itemService.getItemsByBrandAndName(brand, name);
    }

    @PostMapping("/request-item")
    public ResponseEntity<?> processItemRequest(@RequestBody Map<String, Object> request) {
        try {
            String itemName = (String) request.get("itemName");
            String brand = (String) request.get("brand");
            int requestedQuantity = Integer.parseInt(request.get("quantity").toString());

            // Get all distribution centres
            List<DistributionCentre> centres = distributionCentreService.getAllDistributionCentres();
            
            // Find all centres that have the item
            List<Map<String, Object>> availableItems = new ArrayList<>();
            for (DistributionCentre centre : centres) {
                for (Item item : centre.getItemsAvailable()) {
                    if (item.getName().equals(itemName) && item.getBrand().equals(brand)) {
                        Map<String, Object> itemInfo = new HashMap<>();
                        itemInfo.put("centreId", centre.getId());
                        itemInfo.put("centreName", centre.getName());
                        itemInfo.put("item", item);
                        itemInfo.put("availableQuantity", item.getQuantity());
                        availableItems.add(itemInfo);
                    }
                }
            }

            if (availableItems.isEmpty()) {
                return ResponseEntity.badRequest().body("Item not found in any distribution centre");
            }

            // Calculate total available quantity across all centres
            int totalAvailable = availableItems.stream()
                .mapToInt(item -> (Integer) item.get("availableQuantity"))
                .sum();

            if (totalAvailable < requestedQuantity) {
                return ResponseEntity.badRequest().body(
                    "Not enough quantity available across all centres. Total available: " + totalAvailable);
            }

            // Sort centres by available quantity (descending)
            availableItems.sort((a, b) -> 
                ((Integer) b.get("availableQuantity")).compareTo((Integer) a.get("availableQuantity")));

            // Distribute the request across centres
            int remainingQuantity = requestedQuantity;
            StringBuilder responseMessage = new StringBuilder();
            responseMessage.append("Successfully processed request:\n");

            for (Map<String, Object> itemInfo : availableItems) {
                if (remainingQuantity <= 0) break;

                DistributionCentre centre = distributionCentreService.getDistributionCentreById(
                    (Long) itemInfo.get("centreId"));
                Item item = (Item) itemInfo.get("item");
                int availableQuantity = (Integer) itemInfo.get("availableQuantity");
                String centreName = (String) itemInfo.get("centreName");

                int quantityFromCentre = Math.min(remainingQuantity, availableQuantity);
                item.setQuantity(availableQuantity - quantityFromCentre);
                remainingQuantity -= quantityFromCentre;

                distributionCentreService.saveCentre(centre);

                responseMessage.append(String.format("- %d units from %s\n", 
                    quantityFromCentre, centreName));
            }

            return ResponseEntity.ok(responseMessage.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
}
