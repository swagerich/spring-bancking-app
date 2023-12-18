FROM  openjdk:21

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} spring-bank.jar

ENTRYPOINT ["java","-jar","/spring-bank.jar"]

EXPOSE 8080