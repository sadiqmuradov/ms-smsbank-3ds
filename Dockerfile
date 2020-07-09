FROM openjdk:8-jre-alpine

RUN apk update && apk add tzdata && cp /usr/share/zoneinfo/Asia/Baku /etc/localtime
COPY ./build/libs/ms-smsbank-3ds.jar /
COPY ./config-repo/files /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
COPY ./config-repo/files /etc/ssl/certs/java/
RUN mkdir /logs
RUN mkdir /data
COPY ./config-repo/files /
CMD /usr/bin/java -Xmx400m -Xms400m -jar /ms-smsbank-3ds.jar --spring.profiles.active=uat --spring.cloud.config.uri=https://ufctest.pshb.local:30443/config-service
