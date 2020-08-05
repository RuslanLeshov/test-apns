FROM openjdk:13-alpine3.9
COPY ./server/build/libs/server.jar /app/app.jar
ENTRYPOINT java -jar app/app.jar
