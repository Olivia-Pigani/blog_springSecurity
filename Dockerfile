FROM maven:3.9.6-eclipse-temurin-17-alpine AS base

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline


FROM base AS dev

COPY src ./src

CMD ["mvn", "spring-boot:run"]


#FROM base AS build
#
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#
#FROM amazoncorretto:17-alpine3.18 as prod
#
#COPY --from=build /app/target/*.jar app.jar
#
#ENTRYPOINT ["java", "-jar","/app.jar"]