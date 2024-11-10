package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(
        Long id,
        @NotBlank(message = "Username is required")
        @Size(min = 5, message = "Username must be at least 5 characters")
        String username,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        String role,
        String firstname,
        String lastname,
        String address,
        String city,
        String plz,
        String salutation
) {}