package at.technikum.springrestbackend.security.jwt;


import at.technikum.springrestbackend.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtIssuer implements TokenIssuer {

    private final JwtProperties properties;

    public JwtIssuer(JwtProperties properties) {
        this.properties = properties;
    }

    @Override
    public String issue(long id, String username, String role) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("username", username)
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(properties.getSecret()));
    }
}
