package at.technikum.springrestbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Configures the PasswordEncoder that encrypts passwords with the BCrypt algorithm.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures security for the application:
     * - Allows public access to "/api/auth/**" endpoints (e.g., login and register).
     * - Requires users to log in for all other endpoints.
     * - Enables the frontend (CORS) to connect to the backend.
     * - Disables CSRF protection because REST APIs handle security differently (e.g., tokens).
     *
     * @Bean: Tells Spring to create and manage this method's return value
     * (the SecurityFilterChain) as a reusable component in the application.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }


    /**
     * Configures Cross-Origin Resource Sharing (CORS):
     * - Allows your frontend (http://localhost:8081) to send requests to the backend.
     * - Supports specific HTTP methods (e.g., GET, POST, PUT, DELETE).
     * - Accepts all headers from the frontend.
     * - Enables cookies or authentication tokens to be included in requests.
     *
     * @Bean: Marks this method as a reusable component that Spring will use to configure CORS globally.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allows CORS for all endpoints in the backend.
                        .allowedOrigins("http://localhost:8081") // Allows requests from the Vue.js frontend.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specifies which HTTP methods are allowed.
                        .allowedHeaders("*") // Accepts any headers in requests.
                        .allowCredentials(true); // Allows cookies or authentication headers in requests.
            }
        };
    }

}