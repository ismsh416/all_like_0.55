# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Build the JAR
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
