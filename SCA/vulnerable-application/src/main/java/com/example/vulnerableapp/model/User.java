package com.example.vulnerableapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String username;
    
    // Vulnerability: Password stored without annotation for sensitive data
    private String password;
    
    @Email
    private String email;
    
    private String creditCardNumber; // Vulnerability: Storing sensitive data without encryption
    
    private String ssn; // Vulnerability: Storing sensitive data without encryption
    
    // Vulnerability: No input validation for these fields
    private String firstName;
    private String lastName;
    
    // Default constructor
    public User() {}
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password; // Vulnerability: Returning password directly
    }
    
    public void setPassword(String password) {
        this.password = password; // Vulnerability: Setting password without hashing
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCreditCardNumber() {
        return creditCardNumber; // Vulnerability: Returning sensitive data directly
    }
    
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
    
    public String getSsn() {
        return ssn; // Vulnerability: Returning sensitive data directly
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
