# Etapa de build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el pom.xml y descarga dependencias primero
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo el proyecto y compila
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
