# Meerkatgram V2 Post 서비스 구축 및 연동 요약

이 문서는 새롭게 구축한 `meerkatgram-v2-post` 마이크로서비스와 API 게이트웨이(`meerkatgram-v2-scg`) 간의 설정 내용, 구현 방식, 그리고 왜 이러한 방식으로 구조를 설계했는지에 대해 알기 쉽게 정리한 문서입니다.

---

## 1. 무엇을 했는가 (What)

1. **`post` 서비스 초기 뼈대 구축**: 포트 8082번으로 동작하는 새로운 게시글(Post) 마이크로서비스를 구축했습니다.
2. **Gateway API 라우팅 연동**: 사용자가 `/api/posts`로 요청하면, API Gateway(`scg`)가 이를 받아서 `post` 서비스로 전달하도록 설정했습니다.
3. **사용자 식별 정보 연동**: 인증 서비스(`auth`)와 Gateway의 JWT 필터가 검증한 사용자 정보(User ID, Role)를 `post` 서비스가 안전하게 전달받을 수 있도록 구성했습니다.
4. **API 통합 문서화 (Swagger 연동)**: 개별 마이크로서비스의 API 스펙을 Gateway에서 하나로 모아서 볼 수 있도록 설정했습니다.

---

## 2. 어떻게 했는가 (How)

### 📌 Post 서비스 (`meerkatgram-v2-post`)
* **Controller 작성**: `PostController`를 생성하여 `/api/posts` 경로에 매핑(RequestMapping)했습니다. Gateway가 검증 후 넘겨주는 HTTP 헤더(`X-User-Id`, `X-User-Role`)를 `@RequestHeader` 어노테이션으로 읽어서 `%s / %s` 형태로 리턴하도록 코드를 작성했습니다.
* **환경 설정**: Spring Web, Lombok, OpenAPI(Swagger) 의존성을 `build.gradle`에 주입했습니다. 또한 8082 포트를 할당하기 위해 `.env` 파일과 `application.yaml`을 맵핑했습니다.
* **충돌 해결**: `post` 프로젝트 템플릿에 남아있던 Gateway 관련 WebFlux 라이브러리를 제거해 기존의 Spring MVC와 충돌하여 앱이 종료되는 문제를 해결했습니다.

### 📌 API Gateway (`meerkatgram-v2-scg`)
* **라우팅(Routes) 추가**: `application.yaml`과 `.env`에 POST 서비스 라우팅 설정을 신규 등록했습니다. 조건(Predicate)에 따라 `/api/posts/**` 요청이 들어오면 `http://localhost:8082`로 전달되도록 만들었습니다.
* **Swagger UI URL 추가**: Gateway의 Swagger 설정 경로에 `post` 서비스의 API 명세 경로(`/api-docs`)를 추가해 통합했습니다.

---

## 3. 왜 이렇게 했는가 (Why)

* **역할과 책임의 분리 (MSA 패턴)**
  * 인증과 게시글 관리라는 성격이 다른 기능을 `auth`와 `post`라는 별개의 서버로 나눔으로써, 한쪽 서버에 트래픽이 몰리거나 장애가 발생하더라도 다른 서버에 미치는 영향을 최소화합니다.
* **보안 및 인증의 중앙화 (API Gateway 기반 인증)**
  * `post` 서버 내부에는 무거운 JWT 검증 로직이 **전혀 없습니다.** 
  * 외부의 모든 요청은 반드시 게이트웨이(`scg`)를 거치며, SCG 내부의 `AuthFilter`가 토큰의 유효성을 검사합니다. 통과한 안전한 요청에 한해서만 사용자 정보(ID, 권한)를 헤더에 덧붙여서 `post`로 릴레이합니다. 이는 비즈니스 로직(게시글 작성/조회) 개발 시 보안 복잡도를 크게 낮춰줍니다.
* **개발 생산성 향상 (통합 Swagger)**
  * 서비스가 수십 개로 늘어나더라도 클라이언트(프론트엔드) 개발자는 개별 서비스의 API 문서를 일일이 찾아다닐 필요 없이, Gateway 도메인의 `/docs` 경로 하나만 접속하면 모든 서비스의 API를 선택해서 테스트해볼 수 있도록 했습니다.
