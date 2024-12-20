package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.security.jwt.TokenIssuer;
import at.technikum.springrestbackend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;

    @Mock
    private TokenIssuer tokenIssuer;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(tokenIssuer, authenticationManager, userRepository);
    }

    @Test
    void testAuthenticate() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        UserPrincipal userPrincipal = new UserPrincipal(1L, "testuser", "password", "user");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(tokenIssuer.issue(1L, "testuser", "user")).thenReturn("token");

        TokenResponseDto response = authService.authenticate(tokenRequestDto);

        assertNotNull(response.getToken(), "Token should not be null");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenIssuer, times(1)).issue(1L, "testuser", "user");
    }
}
