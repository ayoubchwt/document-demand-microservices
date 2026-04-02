FROM maven:3.9.6-eclipse-temurin-17 AS builder 
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy AS demand-runtime
WORKDIR /app 
COPY --from=builder /build/demand-microservice/target/*.jar app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]

FROM eclipse-temurin:17-jre-jammy AS payment-runtime
WORKDIR /app
COPY --from=builder /build/payment-microservice/target/*jar app.jar
ENTRYPOINT [ "java","-jar","app.jar" ]
