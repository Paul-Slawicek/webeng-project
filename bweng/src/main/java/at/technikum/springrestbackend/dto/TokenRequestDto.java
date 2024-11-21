package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(@NotBlank Long id, @NotBlank String username, @NotBlank String password) {
}
