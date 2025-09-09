# -------- Stage 1: Build --------
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# Copy source
COPY --chown=gradle:gradle . .

# Build application (skip tests for faster build)
RUN gradle clean build -x test

# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy fat JAR from build
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Copy products.json into container
COPY ./data/products.json /app/data/products.json

# Expose API port
EXPOSE 8080

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
