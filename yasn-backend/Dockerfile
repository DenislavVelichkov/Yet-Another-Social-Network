FROM openjdk:11

RUN mkdir -p /app

WORKDIR /app

COPY ./target/yasn-backend-1.0.0-SNAPSHOT.jar /app/

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=container", "yasn-backend-1.0.0-SNAPSHOT.jar"]