
FROM openjdk:17-slim AS builder
WORKDIR /app


RUN apt-get update && \
    apt-get install -y --no-install-recommends curl unzip && \
    rm -rf /var/lib/apt/lists/*


COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src


RUN chmod +x gradlew && \
    ./gradlew bootJar -x test --no-daemon

################################################################################

################################################################################
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app


COPY --from=builder /app/build/libs/*.jar app.jar


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
