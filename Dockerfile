FROM gradle:7.2.0-jdk11 AS build
COPY . /src
WORKDIR /src
RUN ["gradle", "build", "-x", "test"]

FROM openjdk:11.0.11-9-jre-slim
COPY --from=build /src/build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]