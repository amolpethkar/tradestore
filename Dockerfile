# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at the working directory
COPY target/tradestore.jar .

# set the host
ENV HOST=[INSERT_YOUR_HOST_IP]


# Expose the port that your application will run on
EXPOSE 80

# Define the command to run your application when the container starts
CMD ["java", "-jar", "tradestore.jar"]
