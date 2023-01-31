FROM maven:3.6-amazoncorretto-8 AS builder
COPY pom.xml tmp/pom.xml
RUN cd /tmp && \
    mvn dependency:go-offline
COPY src tmp/src
RUN cd /tmp && \
    mvn clean package

FROM amazoncorretto:8
COPY --from=builder /tmp/target/AccountingSystem-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

