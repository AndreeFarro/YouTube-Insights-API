FROM openjdk:18
LABEL authors="AndreeFarro"

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]


