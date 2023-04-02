FROM maven:3-amazoncorretto-17 AS source
WORKDIR /stuff
COPY . .
RUN mvn clean package

FROM amazoncorretto:17-al2-jdk
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=/stuff/target/enterprise-xyz-integration-1.0.0-SNAPSHOT.jar
WORKDIR /app
COPY --from=source ${JAR_FILE} ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]