FROM openjdk:21
COPY ./build/libs/ecobites-0.0.1-SNAPSHOT.jar ecobites-backend.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java","-jar","/ecobites-backend.jar"]
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=postgres