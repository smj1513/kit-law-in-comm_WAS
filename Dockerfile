# Use an official Java runtime as a parent image
FROM azul/zulu-openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the target directory
COPY build/libs/capstone2.jar capstone2.jar

# Run the jar file
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}","-jar", "capstone2.jar"]
