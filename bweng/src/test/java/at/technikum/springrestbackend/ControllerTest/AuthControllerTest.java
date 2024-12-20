package at.technikum.springrestbackend.ControllerTest;

import at.technikum.springrestbackend.controller.AuthController;
import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTokenEndpoint() {
        TokenRequestDto tokenRequestDto = new TokenRequestDto("testuser", "password");
        TokenResponseDto tokenResponseDto = new TokenResponseDto("test-token");

        when(authService.authenticate(tokenRequestDto)).thenReturn(tokenResponseDto);

        TokenResponseDto response = authController.token(tokenRequestDto);

        assertEquals("test-token", response.getToken());
        verify(authService, times(1)).authenticate(tokenRequestDto);
    }
}
