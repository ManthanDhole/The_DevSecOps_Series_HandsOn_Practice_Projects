package com.example.vulnerableapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        // Arrange
        User user = new User();
        
        // Act
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setCreditCardNumber("1234-5678-9012-3456");
        user.setSsn("123-45-6789");
        user.setFirstName("Test");
        user.setLastName("User");
        
        // Assert
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("1234-5678-9012-3456", user.getCreditCardNumber());
        assertEquals("123-45-6789", user.getSsn());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
    }
    
    @Test
    void testUserDefaultConstructor() {
        // Act
        User user = new User();
        
        // Assert
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getCreditCardNumber());
        assertNull(user.getSsn());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
    }
}
