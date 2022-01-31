FROM adoptopenjdk/openjdk11:jdk-11.0.12_7-alpine
COPY /target/hoover-1.0.0.jar hoover.jar
EXPOSE 8080
ARG DATASOURCE_HOST=hoover-db:5432
ENV DATASOURCE_HOST=$DATASOURCE_HOST
ARG DATASOURCE_USERNAME=hoover
ENV DATASOURCE_USERNAME=$DATASOURCE_HOST
ARG DATASOURCE_PASSWORD=hoover
ENV DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD
ENTRYPOINT ["java", "-jar", "hoover.jar", "--spring.datasource.url=jdbc:postgresql://${DATASOURCE_HOST}/hoover", "--spring.datasource.username=${DATASOURCE_USERNAME}", "--spring.datasource.password=${DATASOURCE_PASSWORD}"]
