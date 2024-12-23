package at.technikum.springrestbackend.dto;

public record AdminUserDto(
        String username,
        String email,
        String newPassword,
        String role,
        String firstname,
        String lastname,
        String address,
        String city,
        String plz,
        String salutation,
        String status,
        String picture,
        String country
) {
}
