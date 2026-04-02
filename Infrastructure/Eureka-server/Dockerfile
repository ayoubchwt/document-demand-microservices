FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy AS eureka-runtime
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]