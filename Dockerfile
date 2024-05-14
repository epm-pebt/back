FROM openjdk:21
COPY ./build/libs/ecobites-0.0.1-SNAPSHOT.jar ecobites-backend.jar
ARG username
ENV DB_USERNAME=$username
ARG password
ENV DB_PASSWORD=$password
ENTRYPOINT ["java","-jar","/ecobites-backend.jar"]