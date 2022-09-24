#
# Build stage
#
FROM maven:3.8.6-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:20-slim
COPY --from=build /home/app/target/pontoeletronico-0.0.1-SNAPSHOT.jar /usr/local/lib/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/pontoeletronico-0.0.1-SNAPSHOT.jar"]

##docker run -d -p 8080:8080 --name ponto-eletronico-java  hugopaul/ponto-eletronico-java
##docker build -t=hugopaul/ponto-eletronico-java .