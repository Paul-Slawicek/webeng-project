package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setPassword("plaintextpassword");

        when(passwordEncoder.encode("plaintextpassword")).thenReturn("hashedpassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        assertEquals("hashedpassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("plaintextpassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setPassword("newplaintextpassword");

        when(passwordEncoder.encode("newplaintextpassword")).thenReturn("newhashedpassword");
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUser(user);

        assertEquals("newhashedpassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("newplaintextpassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserWithoutRehashingPassword() {
        User user = new User();
        user.setPassword("alreadyhashedpassword");

        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserWithoutRehashingPassword(user);

        assertEquals("alreadyhashedpassword", user.getPassword());
        verify(passwordEncoder, times(0)).encode(anyString());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testFindAll() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }
}

