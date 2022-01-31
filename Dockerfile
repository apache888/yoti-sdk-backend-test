FROM adoptopenjdk/openjdk11:jdk-11.0.12_7-alpine
COPY /target/hoover-1.0.0.jar hoover.jar
EXPOSE 10500
ENTRYPOINT ["java", "-jar", "hoover.jar", "--spring.datasource.url=jdbc:postgresql://hoover-db:5432/hoover", "--spring.datasource.username=hoover", "--spring.datasource.password=hoover"]
