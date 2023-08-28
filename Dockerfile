FROM alpine:3.17.3 as base
MAINTAINER Yusuf Murodov "yusuf.murodov1@gmail.com"
WORKDIR /app
RUN apk update \
  && apk upgrade \
  && apk add --update --no-cache openjdk17 tzdata curl unzip bash \
  && rm -rf /var/cache/apk/*
COPY . /app
RUN chmod +x ./gradlew \
  && ./gradlew build -x test

FROM base as development
CMD ["./gradlew", "bootRun", "-Dspring.profiles.active=dev", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8001'"]

FROM base as build
RUN ./gradlew jar

FROM openjdk:17 as production
COPY --from=build /app/build/libs/simple-text-crud*.jar /simple-text-crud.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/simple-text-crud.jar"]
