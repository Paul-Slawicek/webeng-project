package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Constructor injection for UserService and PasswordEncoder
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent() ||
                userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username or email is already taken");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        System.out.println("Login attempt with username/email: " + user.getUsername());
        System.out.println("Login attempt with password: " + user.getPassword());

        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isEmpty()) {
            existingUser = userService.findByEmail(user.getUsername());
        }

        if (existingUser.isPresent()) {
            System.out.println("Found user: " + existingUser.get().getUsername());
            System.out.println("Hashed password in DB: " + existingUser.get().getPassword());
            if (passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
                System.out.println("Password matches");
                return ResponseEntity.ok("Login successful");
            } else {
                System.out.println("Password does not match");
            }
        } else {
            System.out.println("User not found");
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }

}
