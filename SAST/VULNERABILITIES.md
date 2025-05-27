# Vulnerabilities in the Spring Boot Application

This document provides a detailed overview of the vulnerabilities deliberately included in this Spring Boot application for educational purposes.

## Security Configuration Issues

### CSRF Protection Disabled
The application has CSRF protection disabled, making it vulnerable to Cross-Site Request Forgery attacks.

```java
http.csrf().disable()
```

### Weak Password Hashing
The application uses BCrypt with a low work factor (4), making password hashing less secure.

```java
return new BCryptPasswordEncoder(4);
```

### All Requests Permitted Without Authentication
The security configuration allows all requests without authentication.

```java
.authorizeRequests()
.anyRequest().permitAll()
```

## Code Vulnerabilities

### SQL Injection
The application contains multiple SQL injection vulnerabilities:

```java
// Direct string concatenation in SQL
String sql = "DELETE FROM user WHERE username = '" + username + "'";
stmt.executeUpdate(sql);
```

```java
// Native queries without proper parameterization
@Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
User findUserByUsername(@Param("username") String username);
```

### Path Traversal
The application allows path traversal through direct use of user input in file paths:

```java
File avatarFile = new File("/tmp/avatars/" + username + ".png");
```

```java
java.nio.file.Path targetPath = java.nio.file.Paths.get(path, file.getOriginalFilename());
file.transferTo(targetPath.toFile());
```

### Command Injection
The application executes commands with user input:

```java
Runtime.getRuntime().exec(command);
```

```java
Process process = Runtime.getRuntime().exec("ping -c 1 " + host);
```

### Cross-Site Scripting (XSS)
The application returns user input directly to the browser without sanitization:

```java
return "<div>Search results for: " + query + "</div>";
```

### Insecure Deserialization
The application deserializes user input without validation:

```java
java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(data));
Object obj = ois.readObject();
```

### XML External Entity (XXE) Processing
The application processes XML without disabling external entity processing:

```java
javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
org.w3c.dom.Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlData.getBytes()));
```

### Server-Side Request Forgery (SSRF)
The application makes requests to URLs provided by users:

```java
java.net.URL targetUrl = new java.net.URL(url);
BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
```

### Open Redirect
The application redirects to user-provided URLs:

```java
response.sendRedirect(url);
```

### Hardcoded Credentials
The application contains hardcoded credentials:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
private static final String DB_USER = "admin";
private static final String DB_PASSWORD = "password123";
```

### Insecure Random Number Generation
The application uses java.util.Random instead of SecureRandom:

```java
Random random = new Random();
```

### Excessive Error Information Disclosure
The application returns detailed error information to clients:

```java
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An error occurred: " + e.getMessage() + "\\n" + getStackTrace(e));
```

## Cryptographic Issues

### Weak Encryption Algorithms
The application uses weak encryption algorithms like DES, MD5, and SHA-1:

```java
Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
```

```java
MessageDigest md = MessageDigest.getInstance("MD5");
```

```java
MessageDigest md = MessageDigest.getInstance("SHA-1");
```

### Hardcoded Encryption Keys
The application uses hardcoded encryption keys:

```java
private static final String SECRET_KEY = "ThisIsASecretKey";
```

### Insecure Encryption Modes
The application uses insecure encryption modes like ECB:

```java
Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
```

### Static Initialization Vectors
The application uses static initialization vectors:

```java
byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
IvParameterSpec ivspec = new IvParameterSpec(iv);
```

## Configuration Issues

### Exposed H2 Console
The application exposes the H2 database console:

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Exposed Actuator Endpoints
The application exposes all actuator endpoints:

```properties
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

### Insecure Cookie Configuration
The application sets insecure cookies:

```java
Cookie cookie = new Cookie("auth", username + ":" + password);
cookie.setPath("/");
// Missing secure and httpOnly flags
response.addCookie(cookie);
```

```properties
server.servlet.session.cookie.http-only=false
server.servlet.session.cookie.secure=false
```

### Excessive Logging
The application logs at DEBUG level:

```properties
logging.level.root=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=DEBUG
```

### Unrestricted File Uploads
The application allows unrestricted file uploads:

```properties
spring.servlet.multipart.max-file-size=-1
spring.servlet.multipart.max-request-size=-1
```

### Insecure CORS Configuration
The application allows all origins, methods, and headers:

```properties
spring.mvc.cors.allowed-origins=*
spring.mvc.cors.allowed-methods=*
spring.mvc.cors.allowed-headers=*
```

## Code Quality Issues

### Duplicate Code Blocks
The application contains duplicate code blocks:

```java
// Duplicate code block 1
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

// Duplicate code block 2
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
```

### Insufficient Test Coverage
The application has approximately 50% test coverage, leaving many vulnerabilities untested.
