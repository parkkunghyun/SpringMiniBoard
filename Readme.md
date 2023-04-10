# 게시판 만들기

## 사용된 기술
- Spring Boot
- Spring MVC
- Spring JDBC
- MYSQL - SQL
- thymeleaf 템플릿 엔진
## 아키텍처

````
브라우저 --- 요청 --> Controller --> Service ---> DAO ---> DB
        <---응답  <--- 템플릿
                <------- Layer간에 데이터 전송은 DTO
````

### 게시판 만든는 순서
1. Controller의 템플릿
2. Service - 비지니스 로직을 처리(하나의 트랜잭션 단위로 처리)
3. Service는 비지니스 로직을 처리하기 위해 데이터를 CRUD하기 위해 DAO 사용
4. 