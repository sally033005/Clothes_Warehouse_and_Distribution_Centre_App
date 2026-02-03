package com.CPAN228.Ass1_Clothes_Warehouse.controller;

import com.CPAN228.Ass1_Clothes_Warehouse.model.User;
import com.CPAN228.Ass1_Clothes_Warehouse.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @org.springframework.beans.factory.annotation.Value("${super.admin.password}")
    private String superAdminPassword;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute User user, @RequestParam("adminPassword") String adminPassword,
            Model model) {
        // Check if username already exists
        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists. Please choose a different username.");
            model.addAttribute("user", user);
            return "registration";
        }

        // Validate super admin password
        if (adminPassword == null || !adminPassword.equals(superAdminPassword)) {
            model.addAttribute("error", "Invalid Super Admin Password. Registration denied.");
            model.addAttribute("user", user);
            return "registration";
        }

        // Validate password length
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters long.");
            model.addAttribute("user", user);
            return "registration";
        }

        // Validate full name
        if (user.getFullName() == null || user.getFullName().trim().length() < 2) {
            model.addAttribute("error", "Full name must be at least 2 characters long.");
            model.addAttribute("user", user);
            return "registration";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login?registered=true";
    }
}