FROM eclipse-temurin:21-jdk AS build
COPY . .
RUN mvn clean install -U -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/cadeachave-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]