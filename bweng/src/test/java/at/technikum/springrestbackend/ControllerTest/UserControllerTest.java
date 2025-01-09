package at.technikum.springrestbackend.ControllerTest;

import at.technikum.springrestbackend.controller.UserController;
import at.technikum.springrestbackend.controller.UserMapper;
import at.technikum.springrestbackend.dto.AdminUserDto;
import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.FileService;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private FileService fileService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper; // Mock für den UserMapper hinzufügen

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com", "password", null, "user", null, null, null, null, null, null, null, null, null);
        User user = new User();

        when(userMapper.toEntity(userDto)).thenReturn(user); // Mock UserMapper
        when(userService.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        var response = userController.registerUser(userDto);

        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com", "password", null, "user", null, null, null, null, null, null, null, null, null);

        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto); // Mock UserMapper

        var response = userController.getUser(1L);

        assertEquals(1L, response.getBody().id());
        verify(userService, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }
    @Test
    void testUpdateUserProfile() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"oldpassword\", \"newPassword\":\"newpassword\"}";
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", "Dummy Image Content".getBytes());

        UserDto userDto = new UserDto(userId, "testuser", "test@example.com", "oldpassword", "newpassword", "user",
                "Test", "User", "Test Address", "City", "12345", "Mr", "test-image.png", "active", "Country");

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("oldpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("oldpassword", currentUser.getPassword())).thenReturn(true);
        when(fileService.upload(profileImage)).thenReturn("uploaded-image.png");

        var response = userController.updateUserProfile(userId, profileDataJson, profileImage);

        assertEquals("User profile updated successfully", response.getBody());
        verify(fileService, times(1)).upload(profileImage);
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }
    @Test
    void testGetProfileImage() throws Exception {
        // Erstelle eine echte temporäre Datei für den Test
        String fileName = "test-image.png";
        File tempFile = File.createTempFile("test-image", ".png");
        tempFile.deleteOnExit();


        when(fileService.getFile(fileName)).thenReturn(tempFile);

        // Rufe die Methode auf
        var response = userController.getProfileImage(fileName);

        // Assertions
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(fileService, times(1)).getFile(fileName);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        AdminUserDto adminUserDto = new AdminUserDto(
                "adminuser", "admin@example.com", "newpassword", "admin", "Admin", "User",
                "Admin Address", "City", "12345", "Mr", "active", "picture.png", "Country");

        User currentUser = new User();
        currentUser.setId(userId);

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));

        // Call the controller method
        var response = userController.updateUser(userId, adminUserDto);

        // Verify the response and interactions
        assertEquals("User updated successfully", response.getBody());
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userService).deleteUser(user);

        var response = userController.deleteUser(userId);

        assertEquals("User deleted successfully", response.getBody());
        verify(userService, times(1)).deleteUser(user);
    }


}
