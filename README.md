<h2> homework batch & api 서버 어플리케이션 실행방법 </h2>

## 초기 환경설정
1. java 1.8
2. maven
3. spring boot

## Project 실행방법
Window - cmd or Mac - 터미널 에서 프로젝트 폴더로 이동 후
1. mvn clean install 명령어 입력
2. mvn compile 명령어 입력
3. mvn package 명령어 입력
4. 프로젝트폴더 -> target 폴더로 이동 후 
5. java -jar homework-0.0.1-SNAPSHOT.jar
6. batch 서버 실행 및 api 서버 실행 완료!! => localhost:8080  꼭!! port = 8080 으로 실행
( front 어플리케이션 rest api end point가 localhost:8080 으로 되어있음 )
