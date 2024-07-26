# Use an official Tomcat runtime as a parent image
FROM tomcat:9.0-jdk8

WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*

# Copy the WAR file to the webapps directory of Tomcat
COPY build/SecondProject.war webapps/SecondProject.war

# Expose port 8080
EXPOSE 8080

# Run Tomcat
CMD ["catalina.sh", "run"]
