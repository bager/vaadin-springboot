FROM eclipse-temurin:21-jre
COPY build/libs/app.jar /app.jar
EXPOSE 8080
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/app.jar"]
