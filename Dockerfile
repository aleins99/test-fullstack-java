FROM openjdk:17-jdk-alpine
RUN mkdir -p /home/test_java
WORKDIR /home/test_java
EXPOSE 8080
COPY target/test_java-0.0.1-SNAPSHOT.jar java_test.jar
CMD ["java","-jar","/home/test_java/java_test.jar"]