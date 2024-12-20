package at.technikum.springrestbackend.SecurityTest;

import at.technikum.springrestbackend.config.JwtProperties;
import at.technikum.springrestbackend.config.SecurityConfig;
import at.technikum.springrestbackend.security.CustomUserDetailService;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.security.jwt.*;
import at.technikum.springrestbackend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtSecurityTest {

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private JwtVerifier jwtVerifier;

    @Mock
    private JwtToPrincipalConverter jwtToPrincipalConverter;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private JwtIssuer jwtIssuer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtProperties.getSecret()).thenReturn("testsecret");
        jwtIssuer = new JwtIssuer(jwtProperties);
    }

    @Test
    void testJwtIssuer() {
        String token = jwtIssuer.issue(1L, "testuser", "ROLE_USER");

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("testsecret"))
                .build()
                .verify(token);

        assertEquals("1", decodedJWT.getSubject());
        assertEquals("testuser", decodedJWT.getClaim("username").asString());
        assertEquals("ROLE_USER", decodedJWT.getClaim("role").asString());
    }

    @Test
    void testJwtVerifier() {
        JwtVerifier verifier = new JwtVerifier(jwtProperties);
        String token = jwtIssuer.issue(1L, "testuser", "ROLE_USER");

        DecodedJWT decodedJWT = verifier.verify(token);

        assertEquals("1", decodedJWT.getSubject());
        assertEquals("testuser", decodedJWT.getClaim("username").asString());
        assertEquals("ROLE_USER", decodedJWT.getClaim("role").asString());
    }

    @Test
    void testJwtToPrincipalConverter() {
        JwtToPrincipalConverter converter = new JwtToPrincipalConverter();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("testsecret"))
                .build()
                .verify(jwtIssuer.issue(1L, "testuser", "ROLE_USER"));

        UserPrincipal principal = converter.convert(decodedJWT);

        assertEquals(1L, principal.getId());
        assertEquals("testuser", principal.getUsername());
        assertEquals("ROLE_USER", principal.getRole());
    }

    @Test
    void testJwtAuthenticationFilter() throws Exception {
        String token = jwtIssuer.issue(1L, "testuser", "ROLE_USER");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtVerifier.verify(anyString())).thenReturn(JWT.decode(token));
        when(jwtToPrincipalConverter.convert(any(DecodedJWT.class)))
                .thenReturn(new UserPrincipal(1L, "testuser", "ROLE_USER", Collections.emptyList().toString()));

        Method doFilterInternal = JwtAuthenticationFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternal.setAccessible(true);
        doFilterInternal.invoke(jwtAuthenticationFilter, request, response, mock(FilterChain.class));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("testuser", ((UserPrincipal) authentication.getPrincipal()).getUsername());
    }

    @Test
    void testCustomUserDetailService() {
        CustomUserDetailService customUserDetailService = new CustomUserDetailService(mock(UserService.class));
        UserPrincipal principal = new UserPrincipal(1L, "testuser", "password", "ROLE_USER");

        when(customUserDetailService.loadUserByUsername("testuser"))
                .thenReturn(principal);

        assertEquals("testuser", principal.getUsername());
        assertEquals("ROLE_USER", principal.getRole());
    }


    @Test
    void testJwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("newsecret");

        assertEquals("newsecret", jwtProperties.getSecret());
    }
}

