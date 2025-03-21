FROM maven:3.9.8-eclipse-temurin-22-alpine AS build

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-alpine

COPY --from=build /app/target/*.jar /app/app.jar

RUN mkdir -p /app/data

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
