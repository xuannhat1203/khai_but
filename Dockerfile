# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Gradle wrapper and build config
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Download dependencies (cached layer)
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# Copy source and build JAR
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Railway injects PORT at runtime; Spring Boot đọc từ application.properties (server.port=${PORT:8080})
EXPOSE 8080

COPY --from=build /app/build/libs/khaibut-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
