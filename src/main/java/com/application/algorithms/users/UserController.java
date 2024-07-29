package com.application.algorithms.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This should match the name of your login HTML file
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        // Add login logic using UserService
        // If successful, redirect to a different page
        if (userService.authenticate(username, password)) {
            return "redirect:/home"; // Redirect to home page or dashboard
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to login page with an error message
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // This should match the name of your registration HTML file
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        // Add registration logic using UserService
        userService.registerUser(user);
        return "redirect:/login"; // Redirect to login page after registration
    }

    // Add other endpoints as needed (e.g., profile management)
}
