package at.technikum.springrestbackend.service;


import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.security.jwt.TokenIssuer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenIssuer tokenIssuer;
    private final AuthenticationManager authenticationManager;

    public AuthService(TokenIssuer tokenIssuer, AuthenticationManager authenticationManager) {
        this.tokenIssuer = tokenIssuer;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponseDto authenticate(TokenRequestDto tokenRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequestDto.username(), tokenRequestDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = tokenIssuer.issue(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRole());
        return new TokenResponseDto(token);
    }
}
