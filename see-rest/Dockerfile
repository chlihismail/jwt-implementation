FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml ./
RUN chmod 1777 /tmp && apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/see-rest-0.0.1-SNAPSHOT.jar"]
