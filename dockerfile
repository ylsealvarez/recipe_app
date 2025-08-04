################################################################################
# 1) Build del frontend
################################################################################
FROM node:18-alpine3.18 AS frontend
WORKDIR /app/recipe-app-frontend
COPY recipe-app-frontend/package*.json ./
RUN npm install
COPY recipe-app-frontend/ ./
RUN npm run build

################################################################################
# 2) Build del backend (Gradle Wrapper + OpenJDK slim)
################################################################################
FROM openjdk:17-slim AS builder
WORKDIR /app
RUN apt-get update && apt-get install -y --no-install-recommends curl unzip && \
    rm -rf /var/lib/apt/lists/*

# Copia sólo lo necesario para el build
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Ejecuta build del jar
RUN chmod +x gradlew && \
    ./gradlew bootJar -x test --no-daemon

################################################################################
# 3) Imagen final mínima
################################################################################
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
