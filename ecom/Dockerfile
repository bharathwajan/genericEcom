# Use Eclipse Temurin OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Declare environment variables (optional default values)
ENV DB_URL="jdbc:postgresql://192.168.29.107:5433/ecom"
ENV DB_USERNAME="postgres"
ENV DB_PASSWORD="sample_password"

# Copy the application JAR file to the container
COPY target/ecom-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9090

# Run the application
CMD ["java", "-jar", "app.jar"]
