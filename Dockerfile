FROM adoptopenjdk/openjdk11:jdk-11.0.12_7-alpine
COPY /target/hoover-1.0.0.jar hoover.jar
EXPOSE 8080
ARG datasource_host=hoover-db:5432
ARG datasource_username=hoover
ARG datasource_password=hoover
ENTRYPOINT ["java", "-jar", "hoover.jar", "--spring.datasource.url=jdbc:postgresql://${datasource_host}/hoover", "--spring.datasource.username=${datasource_username}", "--spring.datasource.password=${datasource_password}"]
