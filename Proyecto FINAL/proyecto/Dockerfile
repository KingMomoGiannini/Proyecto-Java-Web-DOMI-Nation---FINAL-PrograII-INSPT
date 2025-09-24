FROM openjdk:17-jdk-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests
ARG WAR_FILE=target/proyecto-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} proyecto_domination.jar
EXPOSE 8080
CMD ["java", "-jar", "/proyecto_domination.war"]