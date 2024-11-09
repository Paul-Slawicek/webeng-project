# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Kopiere die Maven-Dateien und lade Abhängigkeiten herunter
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiere den gesamten Quellcode und baue das Projekt
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /usr/local/lib
COPY --from=build /app/target/spring-rest-backend-0.0.1.jar app.jar

# Exponiere den Port und starte die Anwendung
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
