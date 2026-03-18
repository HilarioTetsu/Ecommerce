FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Nota el cambio en el nombre del archivo origen
COPY target/Spring-OrderService-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto 8080 (por defecto de Spring Boot)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]