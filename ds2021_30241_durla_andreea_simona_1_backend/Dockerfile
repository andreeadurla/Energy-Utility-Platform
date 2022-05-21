FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/DS2021_30241_Durla_Andreea_Simona_1_Backend-1.0-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/DS2021_30241_Durla_Andreea_Simona_1_Backend-1.0-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-52-49-23-139.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=fpcdntakwmetom
ENV DB_PASSWORD=6500030d2822eeb3c7299453c8461f14b99ba7bdac503549332ebb5065dddf90
ENV DB_DBNAME=dbhe1f9cohp5nb

COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.spreingframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]