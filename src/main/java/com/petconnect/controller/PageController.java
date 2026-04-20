package com.petconnect.controller;

import com.petconnect.model.User;
import com.petconnect.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PetService        petService;
    private final UserService       userService;
    private final AdoptionService   adoptionService;
    private final AppointmentService appointmentService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("availableCount", petService.countAvailable());
        model.addAttribute("adoptedCount",   petService.countAdopted());
        model.addAttribute("userCount",      userService.countUsers());
        model.addAttribute("recentPets",     petService.getAvailablePets().stream().limit(6).toList());
        return "index";
    }

    @GetMapping("/pets")
    public String pets(Model model,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String search) {
        if (search != null && !search.isBlank())
            model.addAttribute("pets", petService.searchPets(search));
        else if (type != null && !type.isBlank())
            model.addAttribute("pets", petService.getPetsByType(type));
        else
            model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("currentType", type);
        return "pets";
    }

    @GetMapping("/pets/{id}")
    public String petDetail(@PathVariable Long id, Model model) {
        petService.getPetById(id).ifPresent(p -> model.addAttribute("pet", p));
        return "pet-detail";
    }

    @GetMapping("/match")
    public String matchPage() { return "match"; }

    @GetMapping("/medical")
    public String medicalPage(Model model) {
        model.addAttribute("pets", petService.getAllPets());
        return "medical"; 
    }

    @GetMapping("/enroll")
    public String enrollPage(Model model) {
        model.addAttribute("pet", new User());
        return "enroll";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        userService.findByEmail(userDetails.getUsername()).ifPresent(u -> {
            model.addAttribute("user", u);
            model.addAttribute("adoptions", adoptionService.getByUser(u.getId()));
            model.addAttribute("appointments", appointmentService.getByUser(u.getId()));
        });
        return "dashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("totalPets",    petService.getAllPets().size());
        model.addAttribute("available",    petService.countAvailable());
        model.addAttribute("adopted",      petService.countAdopted());
        model.addAttribute("totalUsers",   userService.countUsers());
        model.addAttribute("totalAdopt",   adoptionService.countAll());
        model.addAttribute("pendingAdopt", adoptionService.getPending().size());
        model.addAttribute("allPets",      petService.getAllPets());
        model.addAttribute("allUsers",     userService.getAllUsers());
        model.addAttribute("allAdopt",     adoptionService.getAll());
        model.addAttribute("allAppt",      appointmentService.getAll());
        return "admin";
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model) {
        try {
            userService.register(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
