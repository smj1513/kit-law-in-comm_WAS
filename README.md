# 🏛️ LAW-IN-COMM - AI 어시스턴트 기반 법률 상담 커뮤니티 플랫폼

[![CICD](https://github.com/kit-se-capstone2/capstone2_BE_WAS/actions/workflows/main.yml/badge.svg)](https://github.com/kit-se-capstone2/capstone2_BE_WAS/actions/workflows/main.yml)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-blue.svg)](https://www.docker.com/)

> **금오공과대학교 캡스톤 디자인 2 프로젝트**  
> AI와 법률 전문가를 연결하는 혁신적인 법률 상담 플랫폼의 백엔드 서버

---

## 📖 프로젝트 개요

**LAW IN COMM**은 일반 사용자가 법률 상담이 필요할 때 AI 어시스턴트와 전문 변호사로부터 도움을 받을 수 있는 종합 법률 상담 커뮤니티 플랫폼입니다. 

RAG(Retrieval-Augmented Generation) 기술을 활용한 AI 어시스턴트가 유사 판례를 분석하여 1차 답변을 제공하고, 전문 변호사들이 실시간 채팅을 통해 세부 상담을 진행할 수 있는 혁신적인 서비스입니다.

---

## 🎯 핵심 기능

### 🤖 AI 어시스턴트 법률 상담
- **RAG 기반 답변 시스템**: 질문 게시 시 자동으로 AI 서버에 비동기 요청
- **유사 판례 검색**: 과거 판례 데이터베이스를 활용한 정확한 법률 정보 제공
- **웹 검색 연동**: 판례가 없을 경우 웹 검색을 통한 보완 정보 제공

### 👨‍⚖️ 전문 변호사 상담
- **변호사 자격 검증**: 면허증 업로드를 통한 자격 검증 시스템
- **전문 분야별 매칭**: 민사, 형사, 가족법 등 13개 법률 분야별 전문가 매칭
- **실시간 1:1 채팅**: WebSocket 기반 실시간 상담 서비스

### 📝 커뮤니티 기능
- **질문 & 답변 게시판**: 익명/실명 선택 가능한 질문 게시
- **신고 시스템**: 부적절한 답변에 대한 신고 및 관리자 검토
- **마이페이지**: 개인별 질문/답변 이력 관리

### 🔐 보안 및 인증
- **JWT 기반 인증**: Access Token을 통한 안전한 사용자 인증
- **Spring Security**: 역할 기반 접근 제어 (일반사용자/변호사/관리자)
- **파일 보안**: AWS S3를 활용한 안전한 파일 저장

---

## 🏗️ 시스템 아키텍처

### 기술 스택
```
Backend Framework: Spring Boot 3.3.8 (Java 21)
Database: PostgreSQL, Redis
Cloud Storage: AWS S3
Real-time Communication: WebSocket (STOMP)
Documentation: Swagger/OpenAPI 3.0
Containerization: Docker
CI/CD: GitHub Actions
```

### 주요 구성 요소
- **WAS (Web Application Server)**: Spring Boot 기반 REST API 서버
- **AI 서버**: RAG 시스템 기반 법률 상담 AI (별도 서버, Python)
- **데이터베이스**: PostgreSQL (메인), Redis (세션/캐시)
- **파일 저장소**: AWS S3 (프로필 이미지, 자격증 등)

---

## 📊 데이터베이스 설계

### 핵심 엔티티
- **BaseUser** (사용자): 일반사용자와 변호사의 공통 부모 클래스
- **ClientUser** (일반사용자): 질문 작성자
- **Lawyer** (변호사): 답변 제공자, 자격증 및 전문분야 정보 포함
- **Question** (질문): 법률 상담 질문, 법률 분야별 분류
- **Answer** (답변): 변호사의 답변, AI 어시스턴트 답변
- **ChatRoom/ChatMessage** (채팅): 실시간 1:1 상담
- **Report** (신고): 부적절한 콘텐츠 신고 시스템

### 법률 분야 분류
```java
CIVIL_LAW, CRIMINAL_LAW, FAMILY_LAW, LABOR_LAW, 
COMMERCIAL_LAW, ADMINISTRATIVE_LAW, TAX_LAW,
REAL_ESTATE_LAW, INTELLECTUAL_PROPERTY_LAW,
ENVIRONMENT_LAW, MEDICAL_LAW, TRAFFIC_LAW, OTHER
```

---

## 🔧 주요 기능 구현

### AI 답변 자동 생성
```java
// 질문 생성 시 AI 서버로 비동기 요청
@Async
public void sendPostAsyncRequest(AIAnswerRequest aiAnswerRequest){
    restTemplate.postForEntity(url, aiAnswerRequest, Void.class);
}
```

### WebSocket 실시간 채팅
```java
@MessageMapping("/chat/{chatRoomId}")
public void sendMessage(@RequestBody ChatRequest.ChatMessageReq request,
                       @DestinationVariable Long chatRoomId,
                       Principal principal) {
    chatService.sendMessage(request, chatRoomId, principal);
}
```

### JWT 기반 보안
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/users/admin/**").hasRole("ADMIN")
            .requestMatchers("/users/my-page/lawyer/**").hasRole("LAWYER")
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

---

## 🚀 배포 및 운영

### CI/CD 파이프라인
- **GitHub Actions**: 자동 빌드, 테스트, 배포
- **Docker**: 컨테이너화된 애플리케이션 배포
- **Self-hosted Runner**: 자체 서버에서 CI/CD 실행


### 모니터링
- **Swagger UI**: API 문서화 및 테스트 도구
- **Docker Logs**: 컨테이너 로그 모니터링

---

## 📈 기대 효과 및 의의

### 사회적 기대 효과
1. **법률 서비스 접근성 향상**: 24시간 언제든지 법률 상담 가능
2. **비용 절감**: 간단한 상담은 AI로, 복잡한 사안은 전문가로 효율적 분배
3. **법률 정보 민주화**: 일반인도 쉽게 법률 정보에 접근 가능
4. **변호사-고객 매칭**: 전문 분야별 최적의 변호사 연결

### 기술적 의의
1. **RAG 기술 활용**: 법률 도메인에 특화된 AI 모델 구현
2. **마이크로서비스 아키텍처**: WAS와 AI 서버의 분리를 통한 확장성 확보
3. **실시간 통신**: WebSocket을 활용한 즉시성 있는 상담 서비스
4. **클라우드 네이티브**: Docker와 AWS를 활용한 현대적 배포 전략

---

## 🛠️ 개발 과정 및 기술적 도전

### 주요 개발 과정
1. **요구사항 분석** (2주): 법률 상담 프로세스 분석 및 기능 정의
2. **시스템 설계** (2주): 데이터베이스 설계 및 API 명세 작성
3. **백엔드 개발** (8주): Spring Boot 기반 REST API 구현
4. **AI 연동** (2주): RAG 시스템과의 비동기 통신 구현
5. **배포 및 테스트** (2주): Docker 컨테이너화 및 CI/CD 구축

### 해결한 기술적 과제
1. **대용량 파일 처리**: AWS S3 연동으로 프로필 이미지, 자격증 업로드 최적화
2. **실시간 통신**: STOMP over WebSocket으로 안정적인 채팅 시스템 구현
3. **보안 강화**: JWT + Spring Security로 다중 역할 기반 접근 제어
4. **비동기 처리**: AI 답변 생성의 긴 응답 시간을 비동기로 해결
5. **데이터 일관성**: JPA 트랜잭션 관리로 채팅과 알림의 데이터 일관성 보장

---

## 🔄 개발 규칙 및 협업

### Git Convention
- `add`: 새로운 기능 추가
- `modify`: 기존 기능 수정  
- `fix`: 버그 수정
- `docs`: 문서 수정
- `style`: 코드 포맷팅
- `refactor`: 코드 리팩토링
- `test`: 테스트 코드 추가

### API 문서화
- **Swagger UI**: `/api-docs`에서 실시간 API 테스트 가능
- **Bearer Token**: JWT 토큰 기반 인증 테스트 지원

---

## 📚 API 엔드포인트 예시

```bash
# 사용자 회원가입
POST /api/users/join/general
POST /api/users/join/lawyer

# 질문 관리
GET /api/questions
POST /api/questions
PUT /api/questions/{id}
DELETE /api/questions/{id}

# 채팅
GET /api/chatRooms
POST /api/chat/room
GET /api/chat/room/{chatRoomId}

# 관리자
GET /api/users/admin/reports/answers
GET /api/users/admin/confirmations/lawyers
```

---

## 🏆 프로젝트 성과

### 기술적 성과
- **실시간 서비스**: WebSocket 기반 즉시 응답 시스템
- **자동화된 배포**: GitHub Actions를 통한 무중단 배포
- **RESTful API**: 표준 REST 원칙을 준수한 API 설계

### 학습 성과
- **Spring Boot 생태계**: JPA, Security, WebSocket 등 통합 활용
- **클라우드 서비스**: AWS S3, Docker, CI/CD 실무 경험
- **팀 협업**: Git 브랜치 전략, 코드 리뷰, API 문서화
- **문제 해결**: 성능 최적화, 보안 강화, 예외 처리

---

## 🔗 관련 링크

- **Repository**: [GitHub - capstone2_BE_WAS](https://github.com/kit-se-capstone2/capstone2_BE_WAS)
- **AI-MODEL(Embedding)** : [Huggingface](https://huggingface.co/kakao1513/KURE-legal-ft-v1)
- **API Documentation**: Swagger UI (`/api-docs`)
- **CI/CD**: GitHub Actions 워크플로우

---

## 👥 팀 정보

**국립금오공과대학교 컴퓨터공학부 소프트웨어공학전공**  
**캡스톤 디자인 2 프로젝트 (2025년 1학기)**
**성민제, 김민준, 정재운**
---

---
