# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy Gradle wrapper and related files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Make gradlew executable and build the application
RUN chmod +x gradlew
RUN ./gradlew bootJar

# Stage 2: Create the final runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
