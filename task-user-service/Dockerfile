# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080,5001 available to the world outside this container
EXPOSE 8080 5001

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

COPY ./user-service.env .

# Set enviornment variables
ENV SPRING_APP_API_URL \
    SPRING_APP_RUN_STARTUP_FILE \
    SPRING_DATASOURCE_URL \
    SPRING_DATASOURCE_USERNAME \
    SPRING_DATASOURCE_PASSWORD \
    SPRING_APP_JWTSECRET \
    SPRING_APP_JWTEXPIRATIONMS

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
