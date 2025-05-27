FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy gradle files first for better layer caching
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Make the gradlew script executable
RUN chmod +x ./gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build -x test --no-daemon

# Run the application
EXPOSE 8080
CMD ["java", "-jar", "build/libs/vulnerable-app-0.0.1-SNAPSHOT.jar"]
