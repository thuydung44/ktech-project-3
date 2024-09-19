## Shopping mall 

소상공인 인터넷 쇼핑몰을 운영할 수 있게 해주는 쇼핑몰 사이트를 만들어보자.
백엔드만 개발한다. 별도의 프런트엔드 클라이언트가 존재한다고 생각하고 서버를 만들고, Postman으로 테스트를 한다.

## 스택
- Spring Boot 3.3.2
- Spring Boot Data JPA
- SQLite
- Thymeleaf
- Spring Boot Security

## 기술
Java 17 버전
## 실행

1. 본 Repository를 clone 받는다.
2. Intellij IDEA를 이용해 clone 받은 폴더를 연다.
3. [KtechProject3Application.java](src/main/java/com/example/ktech_project_3/KtechProject3Application.java)의 `main`을 실행한다.
4. [buil.gradle](buil.gradle) 파일에서 **database**,  **jsonwebtoken** 활용할 수 있게 몇가지 설치한다. 또한[application.yaml](application.yaml)도 설치해준다.

5. [entity](src/main/java/com/example/ktech_project_3/entity), [dto](src/main//java/com/example/ktech_project_3/dto) package 만들어서 `Entity` 크래스의 데이터를 실제로 주고받고 `Dto` 크래스도 만들어준다. 

## 기본과제
- [사용자 인증 및 권한처리](md/user.md)
- 쇼핑몰 운영하기:
  - [쇼핑몰 개설](md/shop.md)
  - [쇼핑몰 관리](md/product.md)
  - [쇼핑몰 상품 구매](md/order.md)









