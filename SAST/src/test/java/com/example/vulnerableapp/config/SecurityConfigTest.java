package com.example.vulnerableapp.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder_shouldEncodePassword() {
        // Arrange
        String rawPassword = "testPassword";
        
        // Act
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Assert
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
    
    @Test
    void passwordEncoder_shouldVerifyPassword() {
        // Arrange
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Act & Assert
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }
}
