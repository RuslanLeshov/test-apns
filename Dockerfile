FROM openjdk:13-alpine3.9
COPY ./server/build/install/server/ /app/
ENTRYPOINT app/bin/server
