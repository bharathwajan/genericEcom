# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage (lighter JRE)
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV DB_URL="jdbc:postgresql://db:5432/ecom"
ENV DB_USERNAME="postgres"
ENV DB_PASSWORD="sample_password"
COPY --from=builder /build/target/ecom-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Run the application with the production profile
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]