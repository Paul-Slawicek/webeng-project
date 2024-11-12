package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// this interface handles database operations for the User entity
// provides built-in methods to save, update, and delete users
public interface UserRepository extends JpaRepository<User, Long> {

    // returns an Optional, which might contain a User if found
    Optional<User> findByUsername(String username);

    // returns an Optional, which might contain a User if found
    Optional<User> findByEmail(String email);
}
