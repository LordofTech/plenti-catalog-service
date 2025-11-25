# Step 1: Build stage
FROM maven:3.10.1-eclipse-temurin-21-jdk AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar /app/plenti-backend.jar

# Expose port
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "plenti-backend.jar"]
