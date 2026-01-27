package com.CPAN228.distribution_centre_manager.controller;

import com.CPAN228.distribution_centre_manager.data.DistributionCentreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

    private final DistributionCentreRepository distributionCentreRepository;

    public WebController(DistributionCentreRepository distributionCentreRepository) {
        this.distributionCentreRepository = distributionCentreRepository;
    }

    @GetMapping
    public String home() {
        return "redirect:/distribution-centres";
    }

    @GetMapping("/distribution-centres")
    public String distributionCentres(Model model) {
        model.addAttribute("distributionCentres", distributionCentreRepository.findAll());
        return "distribution-centres";
    }
}
