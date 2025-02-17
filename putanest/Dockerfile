# Sử dụng image Java từ Docker Hub
FROM openjdk:17-jdk-slim

# Copy file jar vào container
COPY target/putanest-0.0.1-SNAPSHOT.jar /app/putanest.jar

# Set working directory
WORKDIR /app

# Chạy ứng dụng Spring Boot
CMD ["java", "-jar", "putanest.jar"]


