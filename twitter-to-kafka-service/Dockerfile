FROM openjdk:17-bullseye
ARG RUN_JAVA_VERSION=1.3.8
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

# disable stack trace optimization to log each stack trace
ENV JAVA_OPTIONS="-XX:-OmitStackTraceInFastThrow"

WORKDIR /app
COPY /build/libs/*.jar app.jar

EXPOSE 9001

ENTRYPOINT ["java","-jar","app.jar"]
