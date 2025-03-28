FROM phenompeople/openjdk17:latest

COPY server/target/db ./db

COPY server/target/*.jar server.jar

ENTRYPOINT ["java", "-jar", "server.jar"]