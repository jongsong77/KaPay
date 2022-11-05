# 워크플랫폼팀 개발자 사전 과제

### 과제
- 고객 문의 접수 및 답변 기능 개발

## 프로젝트 구성
### 기본 환경
- STS
- VS Code
### BackEnd(API)
- JAVA 17
- H2
- Spring-Boot
- JPA
- Gradle
### FrontEnd
- Vue.js
- NPM
### 프론트페이지 구조
    └─views
        └─board
            └─ BoardDetail.Vue   //문의 글 상세 페이지 (수정,담당지정,답변)
            └─ BoardList.Vue     //문의 글 리스트 페이지
            └─ BoardWrite.Vue    //문의 글 작성 페이지
        └─login
            └─ LoginForm.Vue     //상담사 로그인 페이지
            └─ LoginFormCust.Vue //고객 로그인 페이지
            └─ LoginForm.Vue     //상담사 로그인 페이지
        └─ PageHome.vue          //메인페이지 (상담사 생성,로그인,문의 리스트 페이지로 접근 가능)
### API LIST
| 번호 | API명 | Method | Description
|---|---|---|---|
1|/qnaList|GET|문의 글리스트 를 가져온다.
2|/agtLogin|POST|상담원 로그인을 요청한다.
3|/singUp|POST| 상담원을 생성한다.
4|/assign|POST|문의글에 상담원을 지정한다.
5|/sendQna|POST|문의글을 작성한다.
6|/qnaDetail|GET|문의글의 상세 정보를 확인한다.
7|/updateQna|POST|작성한 문의글 내용을 수정한다.
8|/replyQna|POST|문의 글에 답변을 작성한다.

## 빌드 및 실행
### 터미널 환경
- Git, Java 는 설치되어 있다고 가정한다.

```
### 백엔드 빌드
$ git clone https://github.com/jongsong77/KaPay.git
$ cd KaPayBE
$ gradlew clean build

### 프론트엔드 빌드
$ npm install
$ npm run build

### 실행 방법 //프론트,백엔드 한번에 실행 가능
$ java -jar /KaPayBE/build/libs/KaPayBE-0.0.1-SNAPSHOT.jar

http://localhost:8001 에서 프로젝트 확인 가능

```

### 문제 해결 전략
  1. 고객은 아이디만으로 문의를 남길 수 있고 작성 글을 및 답변글 확인가능
  - 고객은 id만으로 로그인 처리
  - 문의 의 및 답변 작성 가능하도록 localStorage에 id세팅
  - 고객정보는 DB로 관리 하지않음
  2. 상담원은 로그인후 문의를 확인하고 답변을 남길 수 있다.
  - 로그인 로직 Spring Security를 통해 처리 및 jwt토큰 생성
  - 새로고침시 store정보 유지를 위해 vuex-persistedstate를 사용해 store정보를 localStorage에 저장
  3. 상담원은 문의 담당자를 자신으로 지정한다.
  - 게시글의 상태 column을 생성해 상태를 확인한다.// 0:미상담,1:답변작성중,2:답변완료
  - 답변 작성중 상태의 게시글인 경우 로그인 상태인 상담원과 답변지정한 상담원이 같을 시 재작성 가능.
  - 답변 작성중으로 상태를 변경함으로서 다른 상담원과의 담당지정이 겹치지 않게끔 로직 구성
  - 상태가 0일떄만 담당지정이 가능하고 다른 상담원이 1로 변경시 지정 실패 문구를 안내
  4. 문의 페이지 리스트업
  - 고객은 본인이 작성한 글을 상태별로 확인 가능하게끔 구성
  - 상담원은 작성중, 답변완료 건에 대해서는 본인이 지정한 글만 노출
  - 문의 페이지는 1페이지당 10개 글로 페이징 처리
  - 상담원은 미처리 글 조회시 10초마다 Polling 하여 글을 조회 
  - 작성중,답변완료 조회중일시 Polling을 걸어놓은 setInterval을 clear시킨다.
  - 고객과 상담원의 문의 리스트업 로직은 하나로 통일 하였고 동적파라미터(key,value)에 의해 동적 쿼리를 수행한다.
