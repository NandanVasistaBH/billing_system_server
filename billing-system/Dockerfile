FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/billing-*.jar /app/billing-system.jar

EXPOSE 10000

ENTRYPOINT ["java","-jar", "/app/billing-system.jar"]