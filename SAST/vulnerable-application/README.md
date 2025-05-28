# Vulnerable Spring Boot Application for SAST Testing

This repository contains a deliberately vulnerable Spring Boot application designed for Static Application Security Testing (SAST) demonstration. The application contains over 50 security vulnerabilities across different components, making it ideal for security testing and training.

**WARNING: This application is for educational purposes only and should NEVER be deployed in a production environment.**

## Overview

This Spring Boot application demonstrates common security vulnerabilities including:

- SQL Injection
- Cross-Site Scripting (XSS)
- Command Injection
- Path Traversal
- Insecure Deserialization
- XML External Entity (XXE) Processing
- Server-Side Request Forgery (SSRF)
- Weak Cryptography
- Hardcoded Credentials
- Insecure Configuration
- And many more...

## Prerequisites

### For All Platforms
- Java Development Kit (JDK) 17 or higher
- Gradle 7.0+ (or use the included Gradle wrapper)
- Git

### For Docker
- Docker Engine
- Docker Compose (optional)

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SAST/vulnerable-application
```

### Running the Application

#### Using Gradle Wrapper (Recommended)

##### On macOS/Linux:
```bash
./gradlew bootRun
```

##### On Windows:
```bash
gradlew.bat bootRun
```

#### Using Gradle (if installed):

```bash
gradle bootRun
```

The application will start on `http://localhost:8080`

### Building the Application

#### On macOS/Linux:
```bash
./gradlew build
```

#### On Windows:
```bash
gradlew.bat build
```

This will create a JAR file in the `build/libs` directory.

### Running the JAR file

```bash
java -jar build/libs/vulnerable-app-0.0.1-SNAPSHOT.jar
```

## Docker Setup

### Building the Docker Image

```bash
docker build -t vulnerable-spring-app .
```

### Running the Docker Container

```bash
docker run -p 8080:8080 vulnerable-spring-app
```

### Using Docker Compose

Create a `docker-compose.yml` file:

```yaml
version: '3'
services:
  app:
    build: .
    ports:
      - "8080:8080"
  sonarqube:
    image: sonarqube:latest
    ports:
      - "9000:9000"
```

Then run:

```bash
docker-compose up
```

## Running Tests

### Unit Tests

```bash
./gradlew test
```

### Generate Test Coverage Report

```bash
./gradlew jacocoTestReport
```

The HTML report will be available at `build/jacocoHtml/index.html`

## SonarQube Integration

### Prerequisites
- SonarQube server running (locally or remote)
- SonarQube token for authentication

### Configuration

The project is already configured for SonarQube in the `build.gradle` file. Update the SonarQube properties if needed:

```groovy
sonarqube {
    properties {
        property 'sonar.host.url', 'http://localhost:9000'
        property 'sonar.login', 'your-sonar-token'
    }
}
```

### Running SonarQube Analysis

```bash
./gradlew sonarqube
```

## Platform-Specific Notes

### macOS

If you're using macOS with Apple Silicon (M1/M2), you might need to use the following JVM options:

```bash
./gradlew bootRun -Dorg.gradle.jvmargs="-Djavax.net.ssl.trustStore=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/lib/security/cacerts"
```

### Linux

Ensure you have proper permissions to execute the Gradle wrapper:

```bash
chmod +x ./gradlew
```

### Windows

If you encounter issues with line endings, run:

```bash
git config --global core.autocrlf true
```

## Exploring Vulnerabilities

The application contains vulnerabilities in the following components:

1. **Security Configuration** (`SecurityConfig.java`)
   - CSRF protection disabled
   - Weak password hashing

2. **Controllers** (`UserController.java`)
   - XSS vulnerabilities
   - CSRF vulnerabilities
   - Command injection

3. **Services** (`UserService.java`)
   - SQL Injection
   - Path traversal
   - Insecure random number generation

4. **Utilities** (`EncryptionUtil.java`)
   - Weak encryption algorithms
   - Hardcoded encryption keys
   - Insecure encryption modes

5. **Application Properties** (`application.properties`)
   - Exposed H2 console
   - Exposed actuator endpoints
   - Insecure cookie configuration

## License

This project is licensed under the MIT License - see the LICENSE file for details.
