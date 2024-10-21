FROM gradle:8.10.2-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
COPY --chown=gradle:gradle build.gradle /home/gradle/build.gradle
COPY --chown=gradle:gradle settings.gradle /home/gradle/settings.gradle

RUN gradle build

FROM exoplatform/ubuntu:20.04

ENV JRE_MAJOR_VERSION=17

RUN apt-get -qq update && \
    apt-get -qq -y install gnupg ca-certificates curl
RUN curl -s https://repos.azul.com/azul-repo.key | gpg --dearmor -o /usr/share/keyrings/azul.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/azul.gpg] https://repos.azul.com/zulu/deb stable main" | tee /etc/apt/sources.list.d/zulu.list
RUN apt-get -qq update && \
    apt-get -qq -y install zulu${JRE_MAJOR_VERSION}-jre
RUN apt-get -qq -y autoremove && \
    apt-get -qq -y clean && \
    rm -rf /var/lib/apt/lists/*
ENV JAVA_HOME=/usr/lib/jvm/zulu${JRE_MAJOR_VERSION}-ca-amd64

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/api-0.0.1-SNAPSHOT.jar /app/heat_indicator.jar

ENTRYPOINT ["java","-jar","/app/heat_indicator.jar"]
