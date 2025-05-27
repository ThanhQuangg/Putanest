FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/putanest-0.0.1-SNAPSHOT.jar"]



FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=Lthtrang123
ENV MYSQL_DATABASE=putanest
EXPOSE 3306
