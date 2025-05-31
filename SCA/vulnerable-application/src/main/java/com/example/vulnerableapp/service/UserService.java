package com.example.vulnerableapp.service;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    // Vulnerability: Hardcoded credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password123";
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User saveUser(User user) {
        // Vulnerability: Not hashing passwords before saving
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Vulnerability: SQL Injection
    public void deleteUserByUsername(String username) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            // Vulnerability: Direct string concatenation in SQL
            String sql = "DELETE FROM user WHERE username = '" + username + "'";
            stmt.executeUpdate(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Vulnerability: Path traversal
    public void saveUserAvatar(String username, byte[] avatarData) {
        try {
            // Vulnerability: Path traversal by directly using user input
            File avatarFile = new File("/tmp/avatars/" + username + ".png");
            FileOutputStream fos = new FileOutputStream(avatarFile);
            fos.write(avatarData);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Vulnerability: Insecure random
    public String generateTemporaryPassword() {
        // Vulnerability: Using java.util.Random instead of SecureRandom
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    
    // Vulnerability: Command injection
    public void executeCommand(String command) {
        try {
            // Vulnerability: Directly executing user input as a command
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Duplicate code block 1 - for SonarQube to detect
    public void processUserData(User user) {
        if (user != null) {
            String username = user.getUsername();
            if (username != null && !username.isEmpty()) {
                System.out.println("Processing user: " + username);
                // Some complex logic here
                for (int i = 0; i < 10; i++) {
                    System.out.println("Step " + i);
                    // More processing
                    if (i % 2 == 0) {
                        System.out.println("Even step");
                    } else {
                        System.out.println("Odd step");
                    }
                }
            }
        }
    }
    
    // Duplicate code block 2 - for SonarQube to detect
    public void analyzeUserData(User user) {
        if (user != null) {
            String username = user.getUsername();
            if (username != null && !username.isEmpty()) {
                System.out.println("Processing user: " + username);
                // Some complex logic here
                for (int i = 0; i < 10; i++) {
                    System.out.println("Step " + i);
                    // More processing
                    if (i % 2 == 0) {
                        System.out.println("Even step");
                    } else {
                        System.out.println("Odd step");
                    }
                }
            }
        }
    }
}
