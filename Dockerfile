FROM  exoplatform/ubuntu:20.04

ENV JDK_MAJOR_VERSION=17

# Install OpenJDK Java 17 SDK
RUN apt-get -qq update && \
    apt-get -qq -y install gnupg ca-certificates curl
RUN curl -s https://repos.azul.com/azul-repo.key | gpg --dearmor -o /usr/share/keyrings/azul.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/azul.gpg] https://repos.azul.com/zulu/deb stable main" | tee /etc/apt/sources.list.d/zulu.list
RUN apt-get -qq update && \
    apt-get -qq -y install zulu${JDK_MAJOR_VERSION}-jdk
RUN apt-get -qq -y autoremove && \
    apt-get -qq -y clean && \
rm -rf /var/lib/apt/lists/*
ENV JAVA_HOME=/usr/lib/jvm/zulu${JDK_MAJOR_VERSION}-ca-amd64

COPY build/libs/api-0.0.1-SNAPSHOT.jar /indicator-server-0.0.1.jar
ENTRYPOINT ["java","-jar","/indicator-server-0.0.1.jar"]