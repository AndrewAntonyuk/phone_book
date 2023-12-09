FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar phone_book.jar
ENTRYPOINT ["java", "-jar", "phone_book.jar"]