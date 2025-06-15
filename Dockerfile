# Use lightweight JDK base image
# Optional but helps in multi-arch builds
FROM eclipse-temurin:17-jdk


LABEL authors="pratik.joshi"

# Set working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/spring-boot-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the app's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]