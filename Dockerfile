FROM maven:3.3-jdk-8
COPY . .
RUN mvn clean install

EXPOSE 8080
CMD ["java", "-jar", "target/Bot.jar"]