# Use Amazon Corretto 22 as a parent image
FROM amazoncorretto:22-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar file into the container
COPY target/analytics-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Copy the docker-specific configuration file
COPY src/main/resources/application-docker.yaml /app/application-docker.yaml

# Expose the port (documentation purposes)
EXPOSE 8081

# Set the active profile to docker
ENV SPRING_PROFILES_ACTIVE=docker

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
