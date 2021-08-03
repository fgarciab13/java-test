FROM openjdk:8-jre-slim as runtime-environment

WORKDIR /code

COPY docker_entrypoint.sh /usr/bin/docker_entrypoint.sh
RUN chmod 100 /usr/bin/docker_entrypoint.sh

ENTRYPOINT ["docker_entrypoint.sh"]

FROM maven:3-openjdk-8-slim as build-stage

WORKDIR /code

RUN apt-get update

COPY ./project /code
COPY ./project/src/main/resources/readings.json /code/readings.json
COPY ./project/src/main/resources/readings.yml /code/readings.yml
COPY ./project/src/main/resources/rules.json /code/rules.json

RUN mvn clean compile assembly:single

FROM runtime-environment

COPY --from=build-stage /code/target/test-1.0-jar-with-dependencies.jar /code/test.jar
COPY --from=build-stage /code/readings.json /code/readings.json
COPY --from=build-stage /code/readings.yml /code/readings.yml
COPY --from=build-stage /code/rules.json /code/rules.json