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
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void testGetProfileImageFullIntegration() throws Exception {
        String uploadDir = System.getProperty("java.io.tmpdir") + "/uploads";
        FileService realFileService = new FileService();
        ReflectionTestUtils.setField(realFileService, "uploadDir", uploadDir);

        // Create the controller with the real service
        UserController realUserController = new UserController(userService, passwordEncoder, userMapper, realFileService);

        // Create a temporary file in the upload directory
        String fileName = "test-image.png";
        File tempDir = new File(uploadDir);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File tempFile = new File(tempDir, fileName);
        if (!tempFile.exists()) {
            Files.write(tempFile.toPath(), "Dummy content".getBytes());
        }

        // Call the method to get the profile image
        var response = realUserController.getProfileImage(fileName);

        // Verify the response
        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().exists(), "The returned resource should exist");
        assertEquals(tempFile.getName(), response.getBody().getFilename(), "The returned file name should match");

        // Cleanup
        tempFile.delete();
        tempDir.delete();
    }
    @Test
    void testGetProfileImageFileNotFound() throws Exception {
        String fileName = "non-existent-file.png";

        // Simuliere das Verhalten von fileService.getFile() bei einem Fehler
        when(fileService.getFile(fileName)).thenThrow(new RuntimeException("File not found"));

        // Führe die Controller-Methode aus
        var response = userController.getProfileImage(fileName);

        // Überprüfe den Statuscode
        assertEquals(500, response.getStatusCodeValue());

        // Überprüfe, dass der Body null ist (da ein Fehler aufgetreten ist)
        assertNull(response.getBody(), "Response body should be null on file not found");

        // Verifiziere, dass fileService.getFile() genau einmal aufgerufen wurde
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
    @Test
    void testRegisterUserUsernameTaken() {
        // Arrange
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com", "password", null, "user", null, null, null, null, null, null, null, null, null);
        User existingUser = new User();
        existingUser.setUsername("testuser");
        User userEntity = new User(); // Create a mock User entity
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        // Mock the necessary calls
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(existingUser));

        // Act
        var response = userController.registerUser(userDto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username or email is already taken", response.getBody());
        verify(userService, never()).createUser(any());
    }


    @Test
    void testRegisterUserEmailTaken() {
        UserDto userDto = new UserDto(1L, "testuser", "test@example.com", "password", null, "user", null, null, null, null, null, null, null, null, null);
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        User userEntity = new User(); // Create a mock entity for userMapper.toEntity()
        userEntity.setUsername("testuser");
        userEntity.setEmail("test@example.com");

        when(userMapper.toEntity(userDto)).thenReturn(userEntity); // Mock the toEntity method
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        var response = userController.registerUser(userDto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username or email is already taken", response.getBody());
        verify(userService, never()).createUser(any());
    }

    @Test
    void testUpdateUserProfileInvalidJson() throws Exception {
        Long userId = 1L;
        String invalidProfileDataJson = "invalid-json";

        var response = userController.updateUserProfile(userId, invalidProfileDataJson, null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid profile data format", response.getBody());
        verify(userService, never()).findById(any());
    }

    @Test
    void testUpdateUserProfileIncorrectPassword() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"wrongpassword\", \"newPassword\":\"newpassword\"}";

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("correctpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("wrongpassword", currentUser.getPassword())).thenReturn(false);

        var response = userController.updateUserProfile(userId, profileDataJson, null);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Incorrect current password", response.getBody());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testUpdateUserProfileUserNotFound() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"password\", \"newPassword\":\"newpassword\"}";

        when(userService.findById(userId)).thenReturn(Optional.empty());

        var response = userController.updateUserProfile(userId, profileDataJson, null);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(userService, times(1)).findById(userId);
    }
    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        AdminUserDto adminUserDto = new AdminUserDto(
                "adminuser", "admin@example.com", "newpassword", "admin", "Admin", "User",
                "Admin Address", "City", "12345", "Mr", "active", "picture.png", "Country");

        when(userService.findById(userId)).thenReturn(Optional.empty());

        var response = userController.updateUser(userId, adminUserDto);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(userService, never()).updateUserWithoutRehashingPassword(any());
    }
    @Test
    void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(Optional.empty());

        var response = userController.deleteUser(userId);

        assertEquals(404, response.getStatusCodeValue());
        verify(userService, never()).deleteUser(any());
    }

    @Test
    void testGetAllUsersEmptyList() {
        when(userService.findAll()).thenReturn(List.of());

        var response = userController.getAllUsers();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "Response should contain an empty list");
        verify(userService, times(1)).findAll();
    }

    @Test
    void testUpdateUserProfileWithoutImage() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"oldpassword\", \"newPassword\":\"newpassword\"}";

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("oldpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("oldpassword", currentUser.getPassword())).thenReturn(true);

        var response = userController.updateUserProfile(userId, profileDataJson, null);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        assertEquals("User profile updated successfully", response.getBody());
        verify(fileService, never()).upload(any());
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }
    @Test
    void testRegisterUserInvalidInput() {
        // Arrange: Kein `UserDto` wird übergeben (null)

        // Act: Aufruf der `registerUser`-Methode mit null
        var response = userController.registerUser(null);

        // Assert: Überprüfung des Ergebnisses
        assertEquals(400, response.getStatusCodeValue(), "Response status should be 400");
        assertEquals("Invalid user data", response.getBody(), "Response body should indicate invalid data");
        verify(userService, never()).createUser(any());
    }

    @Test
    void testDeleteUserUnauthorized() {
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(Optional.empty());

        var response = userController.deleteUser(userId);

        assertEquals(404, response.getStatusCodeValue(), "Response status should be 404");
        verify(userService, never()).deleteUser(any());
    }
    @Test
    void testUpdateUserInvalidAdminData() {
        Long userId = 1L;

        var response = userController.updateUser(userId, null);

        assertEquals(404, response.getStatusCodeValue(), "Response status should be 400");
        assertEquals("User not found", response.getBody());
        verify(userService, never()).updateUserWithoutRehashingPassword(any());
    }
    @Test
    void testUpdateUserMissingRole() {
        Long userId = 1L;
        AdminUserDto adminUserDto = new AdminUserDto(
                "adminuser", "admin@example.com", "newpassword", null, "Admin", "User",
                "Admin Address", "City", "12345", "Mr", "active", "picture.png", "Country");

        User currentUser = new User();
        currentUser.setId(userId);

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));

        var response = userController.updateUser(userId, adminUserDto);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }
    @Test
    void testUpdateUserProfileFileUploadFailure() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"oldpassword\", \"newPassword\":\"newpassword\"}";
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", "Dummy Image Content".getBytes());

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("oldpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("oldpassword", currentUser.getPassword())).thenReturn(true);
        doThrow(new IOException("File upload failed")).when(fileService).upload(profileImage);

        var response = userController.updateUserProfile(userId, profileDataJson, profileImage);

        assertEquals(500, response.getStatusCodeValue(), "Response status should be 500");
        assertTrue(response.getBody().toString().contains("Error saving profile image"), "Response body should indicate file upload error");
        verify(fileService, times(1)).upload(profileImage);
        verify(userService, never()).updateUserWithoutRehashingPassword(any());
    }
    @Test
    void testGetProfileImageNotReadable() throws Exception {
        String fileName = "test-image.png";
        File tempFile = File.createTempFile("test-image", ".png");
        tempFile.setReadable(false);
        tempFile.deleteOnExit();

        when(fileService.getFile(fileName)).thenReturn(tempFile);

        var response = userController.getProfileImage(fileName);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        assertNotNull(response.getBody(), "Response body should not be null");
        verify(fileService, times(1)).getFile(fileName);
    }

    @Test
    void testUpdateUserInvalidAdminUserDto() {
        Long userId = 1L;

        var response = userController.updateUser(userId, null);

        assertEquals(404, response.getStatusCodeValue(), "Response status should be 404");
        assertEquals("User not found", response.getBody(), "Response body should indicate user not found");
        verify(userService, never()).updateUserWithoutRehashingPassword(any());
    }
    @Test
    void testUpdateUserWithEmptyFields() {
        Long userId = 1L;
        AdminUserDto adminUserDto = new AdminUserDto(
                "", "", "", "", "", "",
                "", "", "", "", "", "", "");

        User currentUser = new User();
        currentUser.setId(userId);

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));

        var response = userController.updateUser(userId, adminUserDto);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }
    @Test
    void testUpdateUserProfileWithUploadException() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"oldpassword\", \"newPassword\":\"newpassword\"}";
        MockMultipartFile profileImage = new MockMultipartFile("profileImage", "test-image.png", "image/png", "Dummy Image Content".getBytes());

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("oldpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("oldpassword", currentUser.getPassword())).thenReturn(true);
        when(fileService.upload(profileImage)).thenThrow(new IOException("Upload error"));

        var response = userController.updateUserProfile(userId, profileDataJson, profileImage);

        assertEquals(500, response.getStatusCodeValue(), "Response status should be 500 for upload exception");
        assertEquals("Error saving profile image: Upload error", response.getBody());
        verify(fileService, times(1)).upload(profileImage);
        verify(userService, never()).updateUserWithoutRehashingPassword(any());
    }
    @Test
    void testUpdateUserProfileWithoutNewPassword() throws Exception {
        Long userId = 1L;
        String profileDataJson = "{\"username\":\"testuser\", \"password\":\"oldpassword\"}";

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setPassword("oldpassword");

        when(userService.findById(userId)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.matches("oldpassword", currentUser.getPassword())).thenReturn(true);

        var response = userController.updateUserProfile(userId, profileDataJson, null);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        assertEquals("User profile updated successfully", response.getBody());
        verify(fileService, never()).upload(any());
        verify(userService, times(1)).updateUserWithoutRehashingPassword(currentUser);
    }

}
