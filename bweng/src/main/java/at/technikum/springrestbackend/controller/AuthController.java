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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService; // Manages user-related logic.
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder; // Verifies user passwords.
    private final UserMapper userMapper;
    private final JwtIssuer jwtIssuer;// Maps User objects to UserDto and vice versa.

    public AuthController(UserService userService, AuthService authService, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtIssuer jwtIssuer) {
        this.userService = userService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtIssuer = jwtIssuer;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (userService.findByUsername(user.getUsername()).isPresent() ||
                userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username or email is already taken");
        }
        userService.registerUser(user); // Save the user to the database.
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Optional<User> existingUser = userService.findByUsername(user.getUsername());

        if (existingUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            // JWT Token erstellen
            String token = authService.authenticate(new TokenRequestDto(
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
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user.get()));
    }
}
