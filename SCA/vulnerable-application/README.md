# Vulnerable Spring Boot Application for SCA Testing

This repository contains a deliberately vulnerable Spring Boot application designed for Software Composition Analysis (SCA) demonstration. The application contains over 50 security vulnerabilities across different components, making it ideal for security testing and training.

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


## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SCA/vulnerable-application
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