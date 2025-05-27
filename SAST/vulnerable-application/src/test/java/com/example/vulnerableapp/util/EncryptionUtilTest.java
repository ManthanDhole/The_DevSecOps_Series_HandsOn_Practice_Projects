package com.example.vulnerableapp.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilTest {

    @Test
    void encryptDES_shouldEncryptData() {
        // Arrange
        String data = "test-data";
        
        // Act
        String encrypted = EncryptionUtil.encryptDES(data);
        
        // Assert
        assertNotNull(encrypted);
        assertNotEquals(data, encrypted);
    }
    
    @Test
    void decryptDES_shouldDecryptData() {
        // Arrange
        String data = "test-data";
        String encrypted = EncryptionUtil.encryptDES(data);
        
        // Act
        String decrypted = EncryptionUtil.decryptDES(encrypted);
        
        // Assert
        assertEquals(data, decrypted);
    }
    
    @Test
    void hashMD5_shouldReturnHash() {
        // Arrange
        String data = "test-data";
        
        // Act
        String hash = EncryptionUtil.hashMD5(data);
        
        // Assert
        assertNotNull(hash);
        assertEquals(32, hash.length()); // MD5 hash is 32 characters
    }
    
    @Test
    void hashSHA1_shouldReturnHash() {
        // Arrange
        String data = "test-data";
        
        // Act
        String hash = EncryptionUtil.hashSHA1(data);
        
        // Assert
        assertNotNull(hash);
        assertEquals(40, hash.length()); // SHA-1 hash is 40 characters
    }
    
    @Test
    void encryptAESECB_shouldEncryptData() {
        // Arrange
        String data = "test-data";
        
        // Act
        String encrypted = EncryptionUtil.encryptAESECB(data);
        
        // Assert
        assertNotNull(encrypted);
        assertNotEquals(data, encrypted);
    }
    
    @Test
    void encryptAESCBC_shouldEncryptData() {
        // Arrange
        String data = "test-data";
        
        // Act
        String encrypted = EncryptionUtil.encryptAESCBC(data);
        
        // Assert
        assertNotNull(encrypted);
        assertNotEquals(data, encrypted);
    }
    
    @Test
    void processData_shouldHandleNullInput() {
        // Act
        String result = EncryptionUtil.processData(null);
        
        // Assert
        assertEquals("", result);
    }
    
    @Test
    void processData_shouldHandleEmptyInput() {
        // Act
        String result = EncryptionUtil.processData("");
        
        // Assert
        assertEquals("", result);
    }
    
    @Test
    void processData_shouldProcessValidInput() {
        // Arrange
        String input = "Hello, World!";
        
        // Act
        String result = EncryptionUtil.processData(input);
        
        // Assert
        assertEquals("HELLO_WORLD_", result);
    }
    
    @Test
    void formatData_shouldHandleNullInput() {
        // Act
        String result = EncryptionUtil.formatData(null);
        
        // Assert
        assertEquals("", result);
    }
    
    @Test
    void formatData_shouldHandleEmptyInput() {
        // Act
        String result = EncryptionUtil.formatData("");
        
        // Assert
        assertEquals("", result);
    }
    
    @Test
    void formatData_shouldProcessValidInput() {
        // Arrange
        String input = "Hello, World!";
        
        // Act
        String result = EncryptionUtil.formatData(input);
        
        // Assert
        assertEquals("HELLO_WORLD_", result);
    }
}
