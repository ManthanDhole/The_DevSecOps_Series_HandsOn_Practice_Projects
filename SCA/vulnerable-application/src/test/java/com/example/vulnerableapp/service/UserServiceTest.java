package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        users.add(user1);
        
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        users.add(user2);
        
        when(userRepository.findAll()).thenReturn(users);
        
        // Act
        List<User> result = userService.getAllUsers();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }
    
    @Test
    void saveUser_shouldReturnSavedUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@example.com");
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Act
        User result = userService.saveUser(user);
        
        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }
    
    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // Act
        Optional<User> result = userService.getUserById(1L);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }
    
    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act
        Optional<User> result = userService.getUserById(1L);
        
        // Assert
        assertFalse(result.isPresent());
    }
    
    @Test
    void generateTemporaryPassword_shouldReturnPasswordOfCorrectLength() {
        // Act
        String password = userService.generateTemporaryPassword();
        
        // Assert
        assertEquals(10, password.length());
    }
    
    @Test
    void processUserData_shouldHandleNullUser() {
        // Act & Assert - should not throw exception
        userService.processUserData(null);
    }
    
    @Test
    void processUserData_shouldHandleValidUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        
        // Act & Assert - should not throw exception
        userService.processUserData(user);
    }
    
    @Test
    void processUserData_shouldHandleEmptyUsername() {
        // Arrange
        User user = new User();
        user.setUsername("");
        
        // Act & Assert - should not throw exception
        userService.processUserData(user);
    }
    
    @Test
    void processUserData_shouldHandleNullUsername() {
        // Arrange
        User user = new User();
        user.setUsername(null);
        
        // Act & Assert - should not throw exception
        userService.processUserData(user);
    }
    
    @Test
    void analyzeUserData_shouldHandleNullUser() {
        // Act & Assert - should not throw exception
        userService.analyzeUserData(null);
    }
    
    @Test
    void analyzeUserData_shouldHandleValidUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        
        // Act & Assert - should not throw exception
        userService.analyzeUserData(user);
    }
    
    @Test
    void analyzeUserData_shouldHandleEmptyUsername() {
        // Arrange
        User user = new User();
        user.setUsername("");
        
        // Act & Assert - should not throw exception
        userService.analyzeUserData(user);
    }
    
    @Test
    void analyzeUserData_shouldHandleNullUsername() {
        // Arrange
        User user = new User();
        user.setUsername(null);
        
        // Act & Assert - should not throw exception
        userService.analyzeUserData(user);
    }
    
    @Test
    void saveUserAvatar_shouldHandleIOException() {
        // Arrange
        String username = "test";
        byte[] avatarData = "test data".getBytes();
        
        // Act & Assert - should not throw exception
        userService.saveUserAvatar(username, avatarData);
    }
    
    @Test
    void executeCommand_shouldHandleIOException() {
        // Arrange
        String command = "invalid_command";
        
        // Act & Assert - should not throw exception
        userService.executeCommand(command);
    }
    
    @Test
    void executeCommand_shouldExecuteValidCommand() {
        // Arrange
        String command = "echo test";
        
        // Act & Assert - should not throw exception
        userService.executeCommand(command);
    }
}
