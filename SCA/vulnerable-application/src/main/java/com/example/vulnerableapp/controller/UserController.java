package com.example.vulnerableapp.controller;

import com.example.vulnerableapp.model.User;
import com.example.vulnerableapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    // Vulnerability: XSS vulnerability
    @GetMapping("/search")
    public String searchUser(@RequestParam String query, HttpServletResponse response) {
        // Vulnerability: XSS - Directly returning user input without sanitization
        return "<div>Search results for: " + query + "</div>";
    }
    
    // Vulnerability: CSRF vulnerability
    @PostMapping("/update-email")
    public ResponseEntity<String> updateEmail(@RequestParam String email, @RequestParam Long userId) {
        // No CSRF token validation
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(email);
            userService.saveUser(user);
            return ResponseEntity.ok("Email updated");
        }
        return ResponseEntity.notFound().build();
    }
    
    // Vulnerability: Insecure cookie
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, 
                                        HttpServletResponse response) {
        // Vulnerability: Setting insecure cookies
        Cookie cookie = new Cookie("auth", username + ":" + password);
        cookie.setPath("/");
        // Vulnerability: Missing secure and httpOnly flags
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged in");
    }
    
    // Vulnerability: Command injection
    @GetMapping("/ping")
    public String pingHost(@RequestParam String host) {
        try {
            // Vulnerability: Command injection by directly using user input
            Process process = Runtime.getRuntime().exec("ping -c 1 " + host);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\\n");
            }
            return output.toString();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
    
    // Vulnerability: Path traversal
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, 
                                            @RequestParam("path") String path) {
        try {
            // Vulnerability: Path traversal by directly using user input
            java.nio.file.Path targetPath = java.nio.file.Paths.get(path, file.getOriginalFilename());
            file.transferTo(targetPath.toFile());
            return ResponseEntity.ok("File uploaded to: " + targetPath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }
    
    // Vulnerability: Open redirect
    @GetMapping("/redirect")
    public void redirect(@RequestParam String url, HttpServletResponse response) throws IOException {
        // Vulnerability: Open redirect by directly using user input
        response.sendRedirect(url);
    }
    
    // Vulnerability: Server-side request forgery (SSRF)
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchUrl(@RequestParam String url) {
        try {
            // Vulnerability: SSRF by directly using user input
            java.net.URL targetUrl = new java.net.URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            return ResponseEntity.ok(content.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    
    // Vulnerability: XML External Entity (XXE) processing
    @PostMapping("/process-xml")
    public ResponseEntity<String> processXml(@RequestBody String xmlData) {
        try {
            // Vulnerability: XXE by not disabling external entity processing
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlData.getBytes()));
            return ResponseEntity.ok("XML processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing XML: " + e.getMessage());
        }
    }
    
    // Vulnerability: Insecure deserialization
    @PostMapping("/deserialize")
    public ResponseEntity<String> deserializeObject(@RequestBody byte[] data) {
        try {
            // Vulnerability: Insecure deserialization of user input
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(data));
            Object obj = ois.readObject();
            ois.close();
            return ResponseEntity.ok("Object deserialized: " + obj.getClass().getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Deserialization error: " + e.getMessage());
        }
    }
    
    // Vulnerability: Improper error handling exposing sensitive information
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Vulnerability: Exposing stack trace to the client
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage() + "\\n" + getStackTrace(e));
    }
    
    private String getStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\\n");
        }
        return sb.toString();
    }
}
