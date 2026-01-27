package com.CPAN228.Ass1_Clothes_Warehouse.controller;

import com.CPAN228.Ass1_Clothes_Warehouse.data.ItemRepository;
import com.CPAN228.Ass1_Clothes_Warehouse.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.StringBuilder;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Value("${distribution.centre.url:http://localhost:8081/api}")
    private String dcApiBaseUrl;

    public AdminController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/items-management")
    public String manageItems(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        Page<Item> itemPage = itemRepository.findAll(PageRequest.of(page, size).withSort(Sort.by(sortBy)));
        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("page", itemPage);
        return "items-management";
    }

    @PostMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/items-management";
    }

    @GetMapping("/distribution-centres")
    public String viewDistributionCentres(Model model) {
        String url = dcApiBaseUrl + "/distribution-centres";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("manager", "password");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, Object[].class);

        List<Object> centres = Arrays.asList(response.getBody());


        model.addAttribute("centres", centres);
        return "distribution-centres";
    }

    @GetMapping("/request-item")
    public String showRequestItemForm(Model model) {
        String url = dcApiBaseUrl + "/distribution-centres";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("manager", "password");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Object[]> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Object[].class);
            
            List<Object> centres = Arrays.asList(response.getBody());
            model.addAttribute("distributionCentres", centres);

            // Extract unique items from all centres
            Set<Object> uniqueItems = new HashSet<>();
            for (Object centre : centres) {
                Map<String, Object> centreMap = (Map<String, Object>) centre;
                List<Map<String, Object>> items = (List<Map<String, Object>>) centreMap.get("itemsAvailable");
                if (items != null) {
                    uniqueItems.addAll(items);
                }
            }
            model.addAttribute("items", new ArrayList<>(uniqueItems));

        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch data from distribution centres: " + e.getMessage());
        }

        return "request-item-form";
    }

    @PostMapping("/request-item")
    public String requestItem(@RequestParam String itemName,
                            @RequestParam String brand,
                            @RequestParam int quantity,
                            RedirectAttributes redirectAttributes) {
        // First, get all distribution centres
        String centresUrl = dcApiBaseUrl + "/distribution-centres";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("manager", "password");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Get all distribution centres
            ResponseEntity<Object[]> centresResponse = restTemplate.exchange(
                    centresUrl, HttpMethod.GET, entity, Object[].class);
            
            List<Object> centres = Arrays.asList(centresResponse.getBody());
            
            // Find all centres that have the item
            List<Map<String, Object>> availableItems = new ArrayList<>();
            for (Object centre : centres) {
                Map<String, Object> centreMap = (Map<String, Object>) centre;
                List<Map<String, Object>> items = (List<Map<String, Object>>) centreMap.get("itemsAvailable");
                
                if (items != null) {
                    for (Map<String, Object> item : items) {
                        if (itemName.equals(item.get("name")) && brand.equals(item.get("brand"))) {
                            Map<String, Object> itemInfo = new HashMap<>();
                            itemInfo.put("centreId", centreMap.get("id"));
                            itemInfo.put("centreName", centreMap.get("name"));
                            itemInfo.put("availableQuantity", ((Number) item.get("quantity")).intValue());
                            availableItems.add(itemInfo);
                        }
                    }
                }
            }

            if (availableItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", 
                    "Item not found in any distribution centre");
                return "redirect:/admin/request-item";
            }

            // Calculate total available quantity across all centres
            int totalAvailable = availableItems.stream()
                .mapToInt(item -> (Integer) item.get("availableQuantity"))
                .sum();

            if (totalAvailable < quantity) {
                redirectAttributes.addFlashAttribute("error", 
                    "Not enough quantity available across all centres. Total available: " + totalAvailable);
                return "redirect:/admin/request-item";
            }

            // Sort centres by available quantity (descending)
            availableItems.sort((a, b) -> 
                ((Integer) b.get("availableQuantity")).compareTo((Integer) a.get("availableQuantity")));

            // Distribute the request across centres
            int remainingQuantity = quantity;
            StringBuilder successMessage = new StringBuilder();
            successMessage.append("Successfully requested ").append(quantity).append(" units of ")
                         .append(itemName).append(" (").append(brand).append(") from: ");

            for (Map<String, Object> item : availableItems) {
                if (remainingQuantity <= 0) break;

                int availableInCentre = (Integer) item.get("availableQuantity");
                int requestFromCentre = Math.min(remainingQuantity, availableInCentre);
                
                if (requestFromCentre > 0) {
                    // Make the request to this distribution centre
                    String requestUrl = dcApiBaseUrl + "/distribution-centres/request-item";
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("distributionCentreId", item.get("centreId"));
                    requestBody.put("itemName", itemName);
                    requestBody.put("brand", brand);
                    requestBody.put("quantity", requestFromCentre);

                    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

                    ResponseEntity<String> response = restTemplate.exchange(
                            requestUrl, HttpMethod.POST, requestEntity, String.class);

                    if (response.getStatusCode().is2xxSuccessful()) {
                        successMessage.append("\n- ").append(item.get("centreName"))
                                    .append(": ").append(requestFromCentre).append(" units");
                        remainingQuantity -= requestFromCentre;
                    } else {
                        redirectAttributes.addFlashAttribute("error", 
                            "Failed to process request from " + item.get("centreName") + ": " + response.getBody());
                        return "redirect:/admin/request-item";
                    }
                }
            }

            redirectAttributes.addFlashAttribute("success", successMessage.toString());
            return "redirect:/admin/distribution-centres";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to process request: " + e.getMessage());
            return "redirect:/admin/request-item";
        }
    }
}
