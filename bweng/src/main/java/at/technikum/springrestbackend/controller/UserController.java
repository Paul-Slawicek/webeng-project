package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.AdminUserDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        if (userService.findByUsername(user.getUsername()).isPresent() ||
                userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Username or email is already taken");
        }
        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasAuthority('admin')")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userMapper.toDto(users));
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @Validated @RequestBody UserDto userDto) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User currentUser = optionalUser.get();

        if (!passwordEncoder.matches(userDto.password(), currentUser.getPassword())) {
            return ResponseEntity.status(401).body("Incorrect current password");
        }

        userMapper.updateEntityFromDto(userDto, currentUser);

        if (userDto.newPassword() != null && !userDto.newPassword().isEmpty()) {
            currentUser.setPassword(userDto.newPassword());
        }

        userService.updateUser(currentUser);

        return ResponseEntity.ok("User profile updated successfully");
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody AdminUserDto adminUserDto) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User currentUser = optionalUser.get();

        userMapper.updateEntityFromAdminDto(adminUserDto, currentUser);

        if (adminUserDto.newPassword() != null && !adminUserDto.newPassword().isEmpty()) {
            currentUser.setPassword(adminUserDto.newPassword());
        }

        userService.updateUser(currentUser);

        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(user.get());
        return ResponseEntity.ok("User deleted successfully");
    }
}
