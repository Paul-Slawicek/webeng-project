package at.technikum.springrestbackend.security.jwt;

import org.springframework.stereotype.Component;

@Component
public interface TokenIssuer {

    String issue(long id, String username, String role);
}
