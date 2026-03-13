# ---------- Stage 1 : Build ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

# Copy only pom first (for dependency cache)
COPY pom.xml .

RUN mvn -B -q dependency:go-offline

# Copy source
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests


# ---------- Stage 2 : Runtime ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /build/target/*.jar app.jar

# Render uses PORT env variable
ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]