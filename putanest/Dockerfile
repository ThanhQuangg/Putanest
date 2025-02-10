# Giai đoạn build: Cài Maven và build JAR
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn runtime: Sử dụng JDK nhẹ hơn để chạy ứng dụng
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy đúng file JAR được tạo ra từ build
COPY --from=build /app/target/*.jar app.jar

# Chạy ứng dụng với JAR file đã copy
CMD ["java", "-jar", "app.jar"]
