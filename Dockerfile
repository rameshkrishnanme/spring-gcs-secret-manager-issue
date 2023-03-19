## Build Container

FROM maven:3.8.6-openjdk-18-slim as builder

RUN apt-get install -y curl
RUN mkdir -p /workspace
WORKDIR /workspace
ADD . /workspace

RUN mvn clean install -DskipTests

RUN ls -al /workspace/target/


## Runtime Container

FROM openjdk:18-jdk-slim
EXPOSE 8080

ENV GCSFUSE_REPO gcsfuse-stretch

RUN apt-get update && apt-get install --yes --no-install-recommends \
    ca-certificates \
    curl \
    gnupg \
  && echo "deb http://packages.cloud.google.com/apt $GCSFUSE_REPO main" \
    | tee /etc/apt/sources.list.d/gcsfuse.list \
  && curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add - \
  && apt-get update \
  && apt-get install --yes gcsfuse=0.35.1 \
  && apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* 

COPY --from=builder /workspace/target/*.jar /app.jar
CMD java -XX:+UseSerialGC -Xms512m -Xmx2048m -Djava.security.egd=file:/dev/./urandom -jar /app.jar

