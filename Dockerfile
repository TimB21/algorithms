# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project descriptor
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create the final image with JRE
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
