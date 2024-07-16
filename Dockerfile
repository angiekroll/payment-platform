FROM openjdk:17
COPY build/libs/*.jar payment-platform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "payment-platform.jar"]