package com.application.algorithms.users;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        log.info("Received request to create user: {}", user);

        try {
            userRepository.createUser(user);
            log.info("User successfully passed to repository.");
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            log.error("Failed to create user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable int id) {
        log.info("Received request to update user with ID {}: {}", id, user);

        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isEmpty()) {
                log.error("User with ID {} not found. Update operation aborted.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
            }

            userRepository.updateUser(user, id);
            log.info("User with ID {} successfully updated.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User updated successfully");
        } catch (Exception e) {
            log.error("Failed to update user with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user with ID " + id + ": " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        log.info("Received request to delete user with ID {}", id);

        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isEmpty()) {
                log.error("User with ID {} not found. Delete operation aborted.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
            }

            userRepository.deleteUser(id);
            log.info("User with ID {} successfully deleted.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete user with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user with ID " + id + ": " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login.html"; // This should match the name of your login HTML file
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        log.info("Received request to login user with username: {}", username);

        boolean authenticated = userService.authenticate(username, password);
        if (authenticated) {
            log.info("User {} successfully logged in.", username);
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        } else {
            log.error("Login attempt failed for user {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


    @GetMapping("/register")
    public String registerPage() { 
        System.out.println("Accessing /register");
        return "register"; // This should match the name of your registration HTML file
    } 

    
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("Received User object: {}", user);
        
        if (result.hasErrors()) {
            log.error("Validation errors: {}", result.getAllErrors());
            return "register"; // Return to registration page with validation errors
        }
        
        try {
            userService.registerUser(user); // Handle user registration
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login"; // Redirect after successful registration
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            return "register"; // Return to registration page with error
        }
    }
}
