package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null,
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

    public List<UserDto> toDto(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
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

    public void updateEntityFromDto(UserDto userDto, User user) {
        user.setUsername(userDto.username());
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setEmail(userDto.email());
        user.setAddress(userDto.address());
        user.setCity(userDto.city());
        user.setPlz(userDto.plz());
        user.setSalutation(userDto.salutation());
    }

}
