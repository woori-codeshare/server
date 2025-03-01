# Reference: https://spring.io/guides/gs/spring-boot-docker/

FROM openjdk:17-jdk-slim

# 인자 설정 - Jar_File
ARG JAR_FILE=build/libs/*.jar

# jar 파일 복제
COPY ${JAR_FILE} app.jar

# 실행 명령어
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","/app.jar"]
