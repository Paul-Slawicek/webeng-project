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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Deaktiviert CSRF, wenn es nicht benötigt wird (bei REST APIs)
                .cors() // Aktiviert CORS
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // Auth-Endpunkte ohne Authentifizierung zulassen
                .anyRequest().authenticated(); // Alle anderen Endpunkte erfordern Authentifizierung
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Erlaubt CORS für alle Endpunkte
                        .allowedOrigins("http://localhost:8082") // Vue.js-Frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP-Methoden
                        .allowedHeaders("*") // Alle Header erlaubt
                        .allowCredentials(true); // Erlaubt Cookies oder Authentifizierungsheader
            }
        };
    }
}
