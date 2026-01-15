FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw -q -B dependency:go-offline || true

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]