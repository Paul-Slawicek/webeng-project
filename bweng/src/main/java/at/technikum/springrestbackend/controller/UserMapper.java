package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null,
                user.getRole(),
                user.getFirstname(),
                user.getLastname(),
                user.getAddress(),
                user.getCity(),
                user.getPlz(),
                user.getSalutation()
        );
    }

    public User toEntity(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.username(),
                userDto.email(),
                userDto.password(),
                userDto.firstname(),
                userDto.lastname(),
                userDto.address(),
                userDto.city(),
                userDto.plz(),
                userDto.salutation()
        );
    }
}
