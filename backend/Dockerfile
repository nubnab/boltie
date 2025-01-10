FROM maven:3.9.8-eclipse-temurin-22-alpine AS build

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22

RUN apt-get update && apt-get install -y ffmpeg && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar /app/app.jar

RUN mkdir -p /app/data

COPY wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/app/wait-for-it.sh", "mysql:3306", "--","java", "-jar", "/app/app.jar"]
