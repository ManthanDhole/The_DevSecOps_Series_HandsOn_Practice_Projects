package com.example.vulnerableapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Vulnerability: Disabling CSRF protection
        http.csrf().disable()
            .authorizeRequests()
            .anyRequest().permitAll(); // Vulnerability: Allowing all requests without authentication
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Vulnerability: Using a weak work factor for BCrypt
        return new BCryptPasswordEncoder(4);
    }
}
