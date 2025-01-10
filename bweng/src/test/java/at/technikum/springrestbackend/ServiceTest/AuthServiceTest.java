package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.security.jwt.TokenIssuer;
import at.technikum.springrestbackend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @Test
    void testAuthenticateInactiveUser() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        UserPrincipal userPrincipal = new UserPrincipal(1L, "testuser", "password", "user");
        User inactiveUser = new User();
        inactiveUser.setUsername("testuser");
        inactiveUser.setStatus("inactive");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(inactiveUser));

        TokenResponseDto response = authService.authenticate(tokenRequestDto);

        assertNotNull(response, "Response should not be null");
        assertNull(response.getToken(), "Token should be null for inactive user");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(tokenIssuer, never()).issue(anyLong(), anyString(), anyString());
    }
    @Test
    void testAuthenticateUserNotInDatabase() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        UserPrincipal userPrincipal = new UserPrincipal(1L, "testuser", "password", "user");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(tokenIssuer.issue(1L, "testuser", "user")).thenReturn("token");

        TokenResponseDto response = authService.authenticate(tokenRequestDto);

        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getToken(), "Token should not be null");
        assertEquals("token", response.getToken(), "Token should match the issued token");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(tokenIssuer, times(1)).issue(1L, "testuser", "user");
    }

}
