FROM openjdk:17-jdk-alpine
LABEL authors="doguk"

# Uygulama JAR dosyasını container'a kopyalayın
ARG JAR_FILE=target/v2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Uygulamanın çalıştırılma komutunu belirtin
ENTRYPOINT ["java","-jar","/app.jar"]