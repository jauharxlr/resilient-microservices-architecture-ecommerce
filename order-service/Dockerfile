FROM openjdk:21
EXPOSE 8080
ADD build/libs/order-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker", "/app.jar"]