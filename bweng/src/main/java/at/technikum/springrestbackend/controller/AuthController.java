package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles authentication-related requests like registration, login, and user retrieval.
 * @RestController: Marks this class as a controller for handling REST API endpoints.
 * @RequestMapping: Defines the base URL for all endpoints in this controller ("/api/auth").
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService; // Manages user-related logic.
    private final PasswordEncoder passwordEncoder; // Verifies user passwords.
    private final UserMapper userMapper; // Maps User objects to UserDto and vice versa.

    /**
     * Constructor for dependency injection.
     */
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Registers a new user.
     * @param userDto The user data received from the client.
     * @return A success or error response.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto); // Converts DTO to User entity.
        // Check if the username or email is already taken.
        if (userService.findByUsername(user.getUsername()).isPresent() ||
                userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username or email is already taken");
        }
        userService.registerUser(user); // Save the user to the database.
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Logs in a user by verifying their credentials.
     * @param userDto The user's login data (username and password).
     * @return A success or error response.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto); // Converts DTO to User entity.
        Optional<User> existingUser = userService.findByUsername(user.getUsername()); // Check if user exists.

        // Verify the password using the password encoder.
        if (existingUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid username or password"); // Return error if credentials are invalid.
    }

    /**
     * Retrieves a user's data by their ID.
     * @param id The user's ID.
     * @return The user's data or a 404 error if the user is not found.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id); // Retrieve the user by ID.
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if the user is not found.
        }
        return ResponseEntity.ok(userMapper.toDto(user.get())); // Convert the user to a DTO and return it.
    }
}
