FROM eclipse-temurin:21-jdk AS build

# Instala o Maven
RUN apt-get update && \
    apt-get install -y maven

# Copia os arquivos do projeto
COPY . .

# Executa o Maven para construir o projeto
RUN mvn clean install -U -DskipTests

FROM openjdk:21-jdk-slim

# Copia o arquivo JAR construído anteriormente
COPY --from=build /target/cadeachave-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Define o comando de entrada para executar o aplicativo Java
ENTRYPOINT ["java","-jar","app.jar"]
