# Stage 1: Build the application
FROM gradle:7.5.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Stage 2: Run SonarQube analysis
FROM gradle:7.5.0-jdk17 AS sonarqube
WORKDIR /app
COPY --from=builder /app .

# Add SonarQube configuration file
COPY sonar-project.properties .

# Run SonarQube analysis
RUN gradle sonarqube

# Stage 3: Create the final image
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=builder /app/build/libs/meem-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Install MySQL client
RUN apt-get update && apt-get install -y mysql-client
