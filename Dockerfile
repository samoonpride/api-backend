FROM maven:3.8.8-eclipse-temurin-17

# Install FFmpeg
RUN apt-get update && \
    apt-get install -y ffmpeg

WORKDIR /app/api-backend

COPY . .

RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]