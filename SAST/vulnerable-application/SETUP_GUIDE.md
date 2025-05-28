# Detailed Setup Guide for Vulnerable Spring Boot Application

This guide provides detailed instructions for setting up and running the vulnerable Spring Boot application on different operating systems.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Setup on macOS](#setup-on-macos)
- [Setup on Linux](#setup-on-linux)
- [Setup on Windows](#setup-on-windows)
- [Docker Setup](#docker-setup)
- [SonarQube Setup](#sonarqube-setup)
- [Troubleshooting](#troubleshooting)

## Prerequisites

### Java Development Kit (JDK) 17+
The application requires JDK 17 or higher. Here's how to install it on different platforms:

#### macOS
Using Homebrew:
```bash
brew install openjdk@17
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

#### Linux (Fedora/RHEL)
```bash
sudo dnf install java-17-openjdk-devel
```

#### Windows
1. Download the installer from [Adoptium](https://adoptium.net/)
2. Run the installer and follow the instructions
3. Add Java to your PATH environment variable

### Gradle (Optional)
The project includes Gradle Wrapper, so installing Gradle separately is optional.

#### macOS
```bash
brew install gradle
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install gradle
```

#### Windows
1. Download from [gradle.org](https://gradle.org/install/)
2. Extract and add to PATH

### Git
#### macOS
```bash
brew install git
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install git
```

#### Windows
Download and install from [git-scm.com](https://git-scm.com/download/win)

### Docker (Optional)
#### macOS
Download and install [Docker Desktop](https://www.docker.com/products/docker-desktop)

#### Linux
Follow the [official Docker installation guide](https://docs.docker.com/engine/install/)

#### Windows
Download and install [Docker Desktop](https://www.docker.com/products/docker-desktop)

## Setup on macOS

### Clone the Repository
```bash
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SAST/vulnerable-application
```

### Build and Run
```bash
# Make the Gradle wrapper executable
chmod +x ./gradlew

# Build the application
./gradlew build

# If the application fails to build due to incomplete test cases, use the following command, which excludes test task
./gradlew build -x test

# Run the application
./gradlew bootRun
```

### Apple Silicon (M1/M2) Specific Instructions
If you encounter SSL issues on Apple Silicon:
```bash
./gradlew bootRun -Dorg.gradle.jvmargs="-Djavax.net.ssl.trustStore=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/lib/security/cacerts"
```

## Setup on Linux

### Clone the Repository
```bash
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SAST/vulnerable-application
```

### Build and Run
```bash
# Make the Gradle wrapper executable
chmod +x ./gradlew

# Build the application
./gradlew build

# If the application fails to build due to incomplete test cases, use the following command, which excludes test task
./gradlew build -x test

# Run the application
./gradlew bootRun
```

### Memory Considerations
If you're running on a system with limited memory:
```bash
./gradlew bootRun -Dorg.gradle.jvmargs="-Xmx512m -XX:MaxPermSize=256m"
```

## Setup on Windows

### Clone the Repository
```bash
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SAST/vulnerable-application
```

### Build and Run
```bash
# Build the application
gradlew.bat build

# Run the application
gradlew.bat bootRun
```

### Line Ending Issues
If you encounter line ending issues:
```bash
git config --global core.autocrlf true
git clone https://github.com/ManthanDhole/The_DevSecOps_Series_HandsOn_Practice_Projects.git
cd ./SAST/vulnerable-application
```

## Docker Setup

### Building and Running with Docker
```bash
# Build the Docker image
docker build -t vulnerable-spring-app .

# Run the container
docker run -p 8080:8080 vulnerable-spring-app
```

### Using Docker Compose
```bash
# Start both the application and SonarQube
docker-compose up

# Run in detached mode
docker-compose up -d

# Stop the containers
docker-compose down
```

## SonarQube Setup

### Starting SonarQube
#### Using Docker
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
```

#### Using Docker Compose
```bash
docker-compose up sonarqube
```

### Configuring SonarQube
1. Access SonarQube at http://localhost:9000
2. Default credentials: admin/admin
3. Create a new project and generate a token
4. Update the token in `build.gradle`:
   ```groovy
   sonarqube {
       properties {
           property 'sonar.token', 'your-generated-token'
       }
   }
   ```

### Running Analysis
```bash
# On macOS/Linux
./gradlew build -x test
# Run the JaCoCo Test Report for generating Coverage Report
./gradlew jacocoTestReport -x test
# Run the sonar command to upload files to sonarqube server
./gradlew sonar

# On Windows
gradlew.bat sonar
```

## Troubleshooting

### Common Issues

#### Permission Denied for Gradle Wrapper
```bash
chmod +x ./gradlew
```

#### Java Version Issues
Ensure you have JDK 17 or higher:
```bash
java -version
```

#### Port Already in Use
If port 8080 is already in use, modify `application.properties`:
```properties
server.port=8081
```

#### Docker Memory Issues
Increase Docker memory allocation in Docker Desktop settings.

#### SonarQube Connection Issues
Ensure SonarQube is running and accessible:
```bash
curl http://localhost:9000
```

#### Gradle Build Failures
Clean the build directory and try again:
```bash
./gradlew clean build
```
