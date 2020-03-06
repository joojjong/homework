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

## REST API 명세서
### 1. 회원 - 회원가입 
  - DB Table 명 : USERS
  - 설명 : 신규유저를 DB에 저장 
  - POST 방식 :  /user/register
  - 요청주소 : http://localhost:8080/user/register
  - POST RequestBody : UserDto
```java
{
“userId" : “아이디",
"pw" : “비밀번호“,
}
```
  - 반환값 : String  ( “ok” or “fail” )

### 2. 회원 - 로그인 
  - DB Table 명 : USERS
  - 설명 : USERS 테이블에서 userId와 password가 일치하는 회원을 검색하여 존재하면 로그인
  - POST 방식  /user/login
  - 요청주소 : http://localhost:8080/user/login
  - POST RequestBody : UserDto
```java
{
“userId" : “아이디",
"pw" : “비밀번호“,
}
```
  - 반환값 : String ( “ok” or “fail” )

### 3. Open API Data 조회 서버 -> 공공데이터 
  - DB Table 명 : ITEMS
  - 설명 : 공공데이터에서 원하는 건축물대장 데이터를 가져옴
  - GET 방식 : /getBrBasisOulnInfo?sigunguCd=**{시군구코드}**&bjdongCd=**{봉정동코드}**&numOfRows=**{가져올row수}**&ServiceKey=**{서비스키}**&_type=json
  - 요청주소 : http://apis.data.go.kr/1611000/BldRgstService/getBrBasisOulnInfo?sigunguCd=11560&bjdongCd=11800&numOfRows=1&ServiceKey=EJt3X5VcUzwhcTsR7BqCp0dlXcdBHWiEPsWaeJ6lUN6fcIhfxb8X4kwFIjKmP6APcVJBPILeStpY%2B7hTUhTK8w%3D%3D&_type=json
  - 반환값: JSON 객체

### 4. Open API Data 조회  화면 -> 서버
  - DB Table 명 : ITEMS
  - 설명 : ITEMS 테이블에서 조건에 맞는 Item Data( 건축물대장 데이터 ) 조회
  - POST 방식  /openApi/find
  - 요청주소 : http://localhost:8080/openApi/find
  - POST RequestBody : ItemDto
```java
{
“sido" : “시도구분",
“sigunguCd" : “시군구코드“,
“bjdongCd” : “법정동코드",
“platGbCd” : “대지구분코드"
}
```
  - 반환값: List<ItemEntity> 
```java
[
  {
    "rnum": 1,
    "platPlc": "서울특별시 영등포구 도림동 1번지",
    "sigunguCd": "11560",
    "bjdongCd": "11800",
    "platGbCd": "0",
    "bun": "0001",
    "ji": "0000",
    "mgmBldrgstPk": "11560-365",
    "mgmUpBldrgstPk": null,
    "regstrGbCd": "1",
    "regstrGbCdNm": "일반",
    "regstrKindCd": "1",
    "regstrKindCdNm": "총괄표제부",
    "newPlatPlc": " 서울특별시 영등포구 도신로39길 17",
    "bldNm": " ",
    "splotNm": " ",
    "block": " ",
    "lot": " ",
    "bylotCnt": 1,
    "naRoadCd": "115604154330",
    "naBjdongCd": "11802",
    "naUgrndCd": "0",
    "naMainBun": 17,
    "naSubBun": 0,
    "jiyukCd": " ",
    "jiguCd": " ",
    "guyukCd": " ",
    "jiyukCdNm": " ",
    "jiguCdNm": " ",
    "guyukCdNm": " ",
    "crtnDay": "20150130"
  },
  {
    "rnum": 2,
    "platPlc": "서울특별시 영등포구 도림동 1번지",
    "sigunguCd": "11560",
    "bjdongCd": "11800",
    "platGbCd": "0",
    "bun": "0001",
    "ji": "0000",
    "mgmBldrgstPk": "11560-10840",
    "mgmUpBldrgstPk": "11560-365",
    "regstrGbCd": "1",
    "regstrGbCdNm": "일반",
    "regstrKindCd": "2",
    "regstrKindCdNm": "일반건축물",
    "newPlatPlc": " 서울특별시 영등포구 도신로39길 17",
    "bldNm": " ",
    "splotNm": " ",
    "block": " ",
    "lot": " ",
    "bylotCnt": 1,
    "naRoadCd": "115604154330",
    "naBjdongCd": "11802",
    "naUgrndCd": "0",
    "naMainBun": 17,
    "naSubBun": 0,
    "jiyukCd": " ",
    "jiguCd": " ",
    "guyukCd": " ",
    "jiyukCdNm": " ",
    "jiguCdNm": " ",
    "guyukCdNm": " ",
    "crtnDay": "20170728"
  }
] 
```
