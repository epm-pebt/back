FROM openjdk:21
COPY ./build/libs/ecobites-0.0.1-SNAPSHOT.jar ecobites-backend.jar
ENTRYPOINT ["java","-jar","/ecobites-backend.jar"]