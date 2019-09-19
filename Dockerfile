FROM maven:3.3-jdk-8
COPY . .

EXPOSE 8080
CMD ["java", "-jar", "target/Bot.jar"]
