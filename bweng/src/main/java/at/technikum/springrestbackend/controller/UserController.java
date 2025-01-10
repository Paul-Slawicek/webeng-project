package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.AdminUserDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.FileService;
import at.technikum.springrestbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FileService fileService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper, FileService fileService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDto userDto) {
        if (userDto == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
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
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @RequestParam(value = "profileData") String profileDataJson,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        UserDto userDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userDto = objectMapper.readValue(profileDataJson, UserDto.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid profile data format");
        }

        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User currentUser = optionalUser.get();

        if (!passwordEncoder.matches(userDto.password(), currentUser.getPassword())) {
            return ResponseEntity.status(401).body("Incorrect current password");
        }

        userMapper.updateEntityFromDto(userDto, currentUser);

        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String fileName = fileService.upload(profileImage);
                currentUser.setPicture(fileName);
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error saving profile image: " + e.getMessage());
            }
        }

        if (userDto.newPassword() != null && !userDto.newPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(userDto.newPassword()));
        }

        userService.updateUserWithoutRehashingPassword(currentUser);

        return ResponseEntity.ok("User profile updated successfully");
    }

    @GetMapping("/profile-image/{fileName}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
        try {
            File file = fileService.getFile(fileName);
            Resource resource = new UrlResource(file.toURI());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(404).body(null);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
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

        if (adminUserDto.status() != null && !adminUserDto.status().isEmpty()) {
            currentUser.setStatus(adminUserDto.status());
        }

        if (adminUserDto.role() != null && !adminUserDto.role().isEmpty()) {
            currentUser.setRole(adminUserDto.role());
        }

        if (adminUserDto.newPassword() != null && !adminUserDto.newPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(adminUserDto.newPassword()));
        }

        userService.updateUserWithoutRehashingPassword(currentUser);
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
