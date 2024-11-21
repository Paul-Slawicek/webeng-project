package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(@NotBlank String username, @NotBlank String password) {
}
