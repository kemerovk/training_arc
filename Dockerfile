FROM eclipse-temurin:21-jdk-jammy


WORKDIR /app
ARG JAR_FILE=target/*.jar
RUN mkdir -p /app/minio

COPY ${JAR_FILE} app.jar
COPY ./filesToTest/* /app/files/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]
