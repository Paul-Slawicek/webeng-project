package at.technikum.springrestbackend.ControllerTest;

import at.technikum.springrestbackend.controller.UserMapper;
import at.technikum.springrestbackend.dto.AdminUserDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testToDto() {
        // Arrange
        User user = new User(1L, "testuser", "test@example.com", "password",
                "John", "Doe", "123 Street", "City", "12345", "Mr", "Country");

        // Act
        UserDto userDto = userMapper.toDto(user);

        // Assert
        assertEquals(user.getId(), userDto.id());
        assertEquals(user.getUsername(), userDto.username());
        assertEquals(user.getEmail(), userDto.email());
        assertEquals(user.getFirstname(), userDto.firstname());
        assertEquals(user.getLastname(), userDto.lastname());
        assertEquals(user.getAddress(), userDto.address());
        assertEquals(user.getCity(), userDto.city());
        assertEquals(user.getPlz(), userDto.plz());
        assertEquals(user.getSalutation(), userDto.salutation());
        assertEquals(user.getCountry(), userDto.country());
    }

    @Test
    void testToDtoList() {
        // Arrange
        User user1 = new User(1L, "testuser1", "test1@example.com", "password1",
                "John", "Doe", "123 Street", "City", "12345", "Mr", "Country");
        User user2 = new User(2L, "testuser2", "test2@example.com", "password2",
                "Jane", "Smith", "456 Avenue", "Town", "67890", "Ms", "Country");

        List<User> users = List.of(user1, user2);

        // Act
        List<UserDto> userDtos = userMapper.toDto(users);

        // Assert
        assertEquals(2, userDtos.size());
        assertEquals(user1.getUsername(), userDtos.get(0).username());
        assertEquals(user2.getUsername(), userDtos.get(1).username());
    }

    @Test
    void testToEntity() {
        // Arrange
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com", "password",
                null, "user", "John", "Doe", "123 Street", "City", "12345", "Mr",
                null, null, "Country");

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertEquals(userDto.id(), user.getId());
        assertEquals(userDto.username(), user.getUsername());
        assertEquals(userDto.email(), user.getEmail());
        assertEquals(userDto.password(), user.getPassword());
        assertEquals(userDto.firstname(), user.getFirstname());
        assertEquals(userDto.lastname(), user.getLastname());
        assertEquals(userDto.address(), user.getAddress());
        assertEquals(userDto.city(), user.getCity());
        assertEquals(userDto.plz(), user.getPlz());
        assertEquals(userDto.salutation(), user.getSalutation());
        assertEquals(userDto.country(), user.getCountry());
    }

    @Test
    void testUpdateEntityFromAdminDto() {
        // Arrange
        AdminUserDto adminUserDto = new AdminUserDto("adminuser", "admin@example.com",
                "newpassword", "admin", "Alice", "Johnson", "789 Blvd", "Village",
                "54321", "Prof", "active", "admin.jpg", "NewCountry");

        User user = new User(1L, "olduser", "old@example.com", "oldpassword",
                "Bob", "Brown", "987 Road", "OldCity", "98765", "Dr", "OldCountry");

        // Act
        userMapper.updateEntityFromAdminDto(adminUserDto, user);

        // Assert
        assertEquals(adminUserDto.username(), user.getUsername());
        assertEquals(adminUserDto.email(), user.getEmail());
        assertEquals(adminUserDto.firstname(), user.getFirstname());
        assertEquals(adminUserDto.lastname(), user.getLastname());
        assertEquals(adminUserDto.address(), user.getAddress());
        assertEquals(adminUserDto.city(), user.getCity());
        assertEquals(adminUserDto.plz(), user.getPlz());
        assertEquals(adminUserDto.salutation(), user.getSalutation());
        assertEquals(adminUserDto.country(), user.getCountry());
    }
}