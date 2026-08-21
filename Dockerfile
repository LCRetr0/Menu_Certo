FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia TODOS os arquivos do projeto (incluindo o pom.xml e a pasta src)
COPY . .

# Compila o projeto e gera o arquivo .jar
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]