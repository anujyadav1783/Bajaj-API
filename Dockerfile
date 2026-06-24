FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN mvn clean package

EXPOSE 8080

CMD ["java","-jar","target/bfhl-api-0.0.1-SNAPSHOT.jar"]
