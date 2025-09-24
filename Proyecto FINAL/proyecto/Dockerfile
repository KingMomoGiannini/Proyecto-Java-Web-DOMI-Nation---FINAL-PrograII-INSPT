# Etapa de build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests=true -B clean package
# Copia el artefacto (jar o war ejecutable) con nombre estándar
RUN cp target/*.[jw]ar app.jar

# Etapa de runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]