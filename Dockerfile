FROM maven:3.6-amazoncorretto-8 AS build
### copying pom.xml to tmp and resolving dependencies
COPY pom.xml tmp/pom.xml
RUN cd /tmp && \
    mvn dependency:go-offline
### copying source code to tmp + maven
COPY src tmp/src
RUN cd /tmp && \
    mvn clean package

FROM amazoncorretto:8 AS run
### launching jar
COPY --from=build /tmp/target/AccountingSystem-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

