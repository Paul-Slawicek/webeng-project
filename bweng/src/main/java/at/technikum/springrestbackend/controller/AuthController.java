package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.security.jwt.JwtIssuer;
import at.technikum.springrestbackend.service.AuthService;
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
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder; // Verifies user passwords.
    private final UserMapper userMapper;
    private final JwtIssuer jwtIssuer;// Maps User objects to UserDto and vice versa.

    /**
     * Constructor for dependency injection.
     */
    public AuthController(UserService userService, AuthService authService, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtIssuer jwtIssuer){
        this.userService = userService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtIssuer = jwtIssuer;
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
        User user= userMapper.toEntity(userDto); // Convert the DTO to a User entity.
        Optional<User> existingUser = userService.findByUsername(user.getUsername());

        if (existingUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            // JWT Token erstellen
            String token = authService.authenticate(
                    new TokenRequestDto(
                            userDto.username(),
                            userDto.password())).getToken();

            return ResponseEntity.ok(new TokenResponseDto(token)); // Token zurückgeben
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }


    @PostMapping("/token")
    public TokenResponseDto token(@RequestBody @Valid final TokenRequestDto tokenRequestDto) {
        return authService.authenticate(tokenRequestDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id); // Retrieve the user by ID.
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if the user is not found.
        }
        return ResponseEntity.ok(userMapper.toDto(user.get())); // Convert the user to a DTO and return it.
    }
}
