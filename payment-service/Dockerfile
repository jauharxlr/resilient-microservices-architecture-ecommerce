FROM openjdk:21
EXPOSE 8081
ADD build/libs/payment-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "/app.jar"]