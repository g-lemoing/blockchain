FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

#RUN mvn clean package -DskipTests

FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

COPY --from=build /app /app

EXPOSE 3000

# Agregar healthcheck
HEALTHCHECK --interval=10s --timeout=5s --start-period=10s \
    CMD curl -f http://localhost:3000/actuator/health || exit 1

ENTRYPOINT ["mvn", "spring-boot:run"]