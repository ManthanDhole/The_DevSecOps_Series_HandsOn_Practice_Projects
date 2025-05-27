package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        response = new MockHttpServletResponse();
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
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
        
        when(userService.getAllUsers()).thenReturn(users);
        
        // Act
        List<User> result = userController.getAllUsers();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }
    
    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        
        // Act
        ResponseEntity<User> response = userController.getUserById(1L);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testuser", response.getBody().getUsername());
    }
    
    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnNotFound() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<User> response = userController.getUserById(1L);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void createUser_shouldReturnCreatedUser() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");
        savedUser.setEmail("newuser@example.com");
        
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);
        
        // Act
        ResponseEntity<User> response = userController.createUser(user);
        
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("newuser", response.getBody().getUsername());
    }
    
    @Test
    void searchUser_shouldReturnSearchResults() {
        // Act
        String result = userController.searchUser("test", null);
        
        // Assert
        assertEquals("<div>Search results for: test</div>", result);
    }
    
    @Test
    void updateEmail_whenUserExists_shouldReturnOk() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(userService.saveUser(any(User.class))).thenReturn(user);
        
        // Act
        ResponseEntity<String> response = userController.updateEmail("new@example.com", 1L);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email updated", response.getBody());
        verify(userService).saveUser(any(User.class));
    }
    
    @Test
    void updateEmail_whenUserDoesNotExist_shouldReturnNotFound() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<String> response = userController.updateEmail("new@example.com", 1L);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).saveUser(any(User.class));
    }
    
    @Test
    void login_shouldSetCookieAndReturnOk() {
        // Act
        ResponseEntity<String> result = userController.login("testuser", "password", response);
        
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Logged in", result.getBody());
        assertNotNull(response.getCookie("auth"));
        assertEquals("testuser:password", response.getCookie("auth").getValue());
    }
}
