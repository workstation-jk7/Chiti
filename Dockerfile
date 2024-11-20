FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18.0.1-jdk
COPY --from=build /target/chiti-0.0.1-SNAPSHOT.jar chiti.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/chiti.jar"]