FROM zenika/alpine-kotlin
COPY ./server/build/libs/server.jar /app/app.jar
ENTRYPOINT java -jar app/app.jar
