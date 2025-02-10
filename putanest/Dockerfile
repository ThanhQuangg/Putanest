# Giai đoạn build: Cài Maven và build JAR
FROM maven:3.8.6-openjdk-17 AS build

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ source code vào container
COPY . .

# Chạy lệnh Maven để build JAR, bỏ qua test cho nhanh
RUN mvn clean package -DskipTests

# Giai đoạn runtime: Sử dụng JDK nhỏ hơn để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ giai đoạn build
COPY --from=build /app/target/*.jar app.jar

# Cấu hình start command để chạy ứng dụng
CMD ["java", "-jar", "putanest-0.0.1-SNAPSHOT.jar"]
