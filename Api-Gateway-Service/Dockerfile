FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy your jar
COPY target/Api-Gateway-Service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
