package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class provides methods for managing users:
 * - Registering new users (with hashed passwords).
 * - Searching for users by email, username, or ID.
 *
 * @Service: Marks this class as a service layer that handles business logic.
 */
@Service
public class UserService {

    private final UserRepository userRepository; // Handles database interactions.
    private final PasswordEncoder passwordEncoder; // Used to hash and verify passwords.

    /**
     * Constructor to initialize dependencies for UserRepository and PasswordEncoder.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user:
     * - The password is securely hashed before saving to the database.
     * @param user The user to be registered.
     * @return The saved user object.
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password for security.
        return userRepository.save(user); // Save the user in the database.
    }

    /**
     * Searches for a user by email address.
     * @param email The email to search for.
     * @return An Optional containing the user, if found.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Searches for a user by username.
     * @param username The username to search for.
     * @return An Optional containing the user, if found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Searches for a user by their ID.
     * @param id The user's ID.
     * @return An Optional containing the user, if found.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
