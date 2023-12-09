# FROM maven:3.8.3-jdk-11 AS build
# COPY . /app
# WORKDIR /app
# RUN mvn package -DskipTests
#
# # Second stage: create a slim image
# FROM openjdk:11-jre-slim
# COPY --from=build /app/target/phone_book.jar /phone_book.jar
# ENTRYPOINT ["java", "-jar", "/phone_book.jar"]


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar phone_book.jar
ENTRYPOINT ["java", "-jar", "phone_book.jar"]