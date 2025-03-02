# Use an official OpenJDK runtime as the base image (Java 21)
FROM eclipse-temurin:21-jdk-jammy

  # Set the working directory inside the container
WORKDIR /app

  # Copy the JAR file into the container
COPY target/your-application-name.jar app.jar

  # Expose the port your application runs on
EXPOSE 8080

  # Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]