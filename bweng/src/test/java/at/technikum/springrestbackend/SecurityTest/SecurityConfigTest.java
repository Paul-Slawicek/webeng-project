package at.technikum.springrestbackend.SecurityTest;

import at.technikum.springrestbackend.config.JwtProperties;
import at.technikum.springrestbackend.config.SecurityConfig;
import at.technikum.springrestbackend.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private final JwtAuthenticationFilter jwtAuthenticationFilter = mock(JwtAuthenticationFilter.class);
    private final SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationFilter);

    @Test
    void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    void testSecurityFilterChain() throws Exception {
        // Mock HttpSecurity und seine Rückgabewerte
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.formLogin(any())).thenReturn(httpSecurity);
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)).thenReturn(httpSecurity);

        // Rufe die Methode auf
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity);

        // Assertions
        assertNotNull(filterChain);

        // Verifiziere, dass alle relevanten Methoden aufgerufen wurden
        verify(httpSecurity, times(1)).csrf(any());
        verify(httpSecurity, times(1)).formLogin(any());
        verify(httpSecurity, times(1)).cors(any());
        verify(httpSecurity, times(1)).sessionManagement(any());
        verify(httpSecurity, times(1)).authorizeHttpRequests(any());
        verify(httpSecurity, times(1)).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Test
    void testSecurityFilterChainException() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(httpSecurity.build()).thenThrow(new RuntimeException("Test exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            securityConfig.filterChain(httpSecurity);
        });

        assertEquals("Test exception", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testJwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("newsecret");

        assertEquals("newsecret", jwtProperties.getSecret());
    }
}
