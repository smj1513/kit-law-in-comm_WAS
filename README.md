# 🏛️ LAW-IN-COMM: AI 어시시턴트 기반 법률 상담 커뮤니티 플랫폼

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.8-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-blue.svg)](https://www.docker.com/)

> **금오공과대학교 캡스톤 디자인 2 프로젝트**
> AI와 법률 전문가를 연결하여 법률 서비스의 문턱을 낮추는 지능형 법률 상담 플랫폼의 백엔드 서버를 개발했습니다.

---

👥 팀 정보
소속: 국립금오공과대학교 컴퓨터공학부 소프트웨어전공 (2025년 1학기 캡스톤 디자인 2)

팀원: 성민제, 김민준, 정재운

**주요 역할 및 기여** <br>
> 저는 본 프로젝트에서 백엔드 개발, 인프라 구축, AI 모델링 등 3개의 핵심 영역을 담당하며 플랫폼의 기술적 기반을 다졌습니다.

**Back-End (Application Architecture & Development)**

> Spring Boot 기반 WAS(Web Application Server) 전체 설계 및 구현을 총괄했습니다.

> DDD(도메인 주도 설계)를 적용하여 확장 가능하고 유지보수가 용이한 아키텍처를 설계했습니다.

> 실시간 채팅을 위한 WebSocket API, 보안을 위한 JWT 인증 등 핵심 비즈니스 로직을 구현했습니다.

**Infrastructure (System & DevOps)**

> On-premise 서버 환경의 인프라를 직접 구축하고 운영했습니다.

> Docker를 이용한 컨테이너화로 개발 및 배포 환경의 일관성을 확보했습니다.

> GitHub Actions 기반의 CI/CD 파이프라인을 설계 및 구축하여 코드 통합부터 배포까지의 전 과정을 자동화하고 개발 생산성을 향상시켰습니다.

**Artificial Intelligence (AI Modeling)**

> 법률 상담 도메인에 특화된 답변 생성을 위해 임베딩 모델을 선정하고 법률 질의 응답 데이터 셋을 활용하여 Fine-tuning을 통해 성능을 최적화했습니다.

---


## 📖 1. 프로젝트 소개

### **LAW-IN-COMM 이란?**

**LAW-IN-COMM**은 법률 문제로 어려움을 겪는 일반 사용자가 AI 어시스턴트와 전문 변호사로부터 신속하고 정확한 도움을 받을 수 있는 종합 법률 상담 커뮤니티 플랫폼입니다. 복잡한 법률 용어나 절차에 대한 막막함을 해소하고, 누구나 합리적인 비용으로 법률 전문가의 조력을 받을 수 있는 환경을 제공하는 것을 목표로 합니다.

### **프로젝트 목표**

1.  **AI 기술을 활용한 1차 법률 상담 자동화**: RAG(Retrieval-Augmented Generation) 기술을 적용하여 유사 판례 기반의 신뢰도 높은 AI 답변을 제공함으로써 사용자의 궁금증을 빠르게 해소합니다.
2.  **전문가와의 접근성 강화**: 검증된 변호사들과 사용자를 직접 연결하는 실시간 채팅 기능을 통해 심도 있는 2차 상담을 지원합니다.
3.  **안정적이고 확장 가능한 백엔드 시스템 구축**: 대규모 트래픽과 실시간 통신을 안정적으로 처리할 수 있도록 Spring Boot 기반의 견고한 서버 아키텍처를 설계하고 구현합니다.

---

## 🎯 2. 핵심 기능

| 기능 분류 | 주요 기능 | 설명 |
| :--- | :--- | :--- |
| **🤖 AI 법률 상담** | **RAG 기반 답변 시스템** | 질문 등록 시, AI 서버에 비동기 요청을 보내 국내 판례를 기반으로 한 1차 답변을 자동으로 생성하여 제공합니다. |
| | **웹 검색 연동** | 관련 판례가 부족할 경우, 웹 검색 결과를 통합하여 보완적인 정보를 제공함으로써 답변의 완성도를 높입니다. |
| **👨‍⚖️ 전문가 1:1 상담** | **실시간 채팅 상담** | WebSocket(STOMP) 기반의 1:1 채팅 기능을 통해 사용자와 변호사 간의 원활하고 즉각적인 소통을 지원합니다. |
| | **변호사 자격 검증** | 변호사 면허증 업로드 및 관리자 승인 절차를 통해 플랫폼의 신뢰도를 확보합니다. |
| **📝 커뮤니티** | **질문/답변 게시판** | 사용자는 익명 또는 실명으로 법률 질문을 게시하고, 변호사 및 다른 사용자들과 자유롭게 소통할 수 있습니다. |
| | **신고 및 관리 시스템** | 부적절한 게시물이나 답변을 신고하고 관리자가 검토하여 건전한 커뮤니티 환경을 유지합니다. |
| **🔐 보안 및 인증** | **역할 기반 접근 제어** | Spring Security와 JWT를 활용하여 일반 사용자, 변호사, 관리자별 권한을 명확히 분리하고 API 접근을 통제합니다. |
| | **안전한 파일 관리** | AWS S3를 통해 사용자 프로필, 변호사 자격증 등 민감한 파일을 안전하게 저장하고 관리합니다. |

### 2.1 사용자 인터페이스

**메인화면**<br>
<img width="1256" height="1239" alt="image" src="https://github.com/user-attachments/assets/9bb3f45e-5a5e-4d16-b6d4-3d34196a8fcc" /><br>
**로그인**<br>
<img width="1272" height="1037" alt="image" src="https://github.com/user-attachments/assets/056ccddc-0990-42ee-863a-c05085147f0a" /><br>
**회원 가입 진입**<br>
<img width="1273" height="1258" alt="image" src="https://github.com/user-attachments/assets/0e36249e-97b2-4401-be78-4516b3c09d08" /><br>
**변호사 회원가입**<br>
<img width="1256" height="1258" alt="image" src="https://github.com/user-attachments/assets/09aff84d-bbef-4b79-b748-6746ee4466cc" /><br>
<img width="1262" height="1261" alt="image" src="https://github.com/user-attachments/assets/269bf373-b111-48d3-b8c7-307e6fe131d1" /><br>
**사용자 질문**<br>
<img width="973" height="534" alt="image" src="https://github.com/user-attachments/assets/6b6176e7-dbf9-48cf-ad20-a3c621c42621" /><br>
**AI 어시스턴트 답변**<br>
<img width="761" height="1110" alt="image" src="https://github.com/user-attachments/assets/485a3d8e-40ee-4993-becb-8f3ea2e76652" /><br>
<img width="757" height="545" alt="image" src="https://github.com/user-attachments/assets/162397d5-3af3-4f06-b6a0-2061b89ea8c4" /><br>
**사용자 질문 작성**<br>
<img width="1256" height="1256" alt="image" src="https://github.com/user-attachments/assets/bc3011df-4eda-4471-8a04-054494b186e5" /><br>
**채팅**<br>
<img width="378" height="559" alt="image" src="https://github.com/user-attachments/assets/43d27eb9-ca21-4b7f-8893-bd7eb359ee57" /><br>







---

## 🏗️ 3. 시스템 아키텍처


### **전체 구성도**
- 캡스톤 디자인 프로젝트의 특성과 목표를 고려하여, WAS의 경우 신속한 프로토타이핑에 유리한 모놀리식 아키텍처로 설계를 진행했습니다. 단, AI 처리는 별도의 FastAPI 서버를 구성하였습니다. 또한, 서버 GPU 자원 활용의 제약으로 인해 클라우드 환경 대신 자체 물리 서버를 활용하는 온프레미스 방식으로 시스템을 구축하고 운영하고 있습니다.

<img width="645" height="597" alt="image" src="https://github.com/user-attachments/assets/47b2a635-64d4-4ec2-9fcf-9550304fc34f" />

### **기술 스택**

| 구분 | 기술 | 선택 이유 |
| :--- | :--- | :--- |
| **Backend** | `Java 21`, `Spring Boot 3.3.8` | 안정성과 생산성이 높고, 강력한 생태계를 통해 JPA, Security 등 다양한 기술을 유연하게 통합할 수 있습니다. |
| **Database** | `PostgreSQL`, `Redis` | PostgreSQL은 복잡한 관계형 데이터 모델링에 적합하며, Redis는 캐싱이나 실시간 순위 시스템에 활용될 수 있습니다. |
| **Real-time** | `WebSocket (STOMP)` | 실시간 1:1 채팅 기능 구현을 위해 양방향 통신이 가능한 WebSocket과 메시징 프로토콜 STOMP를 채택했습니다. |
| **Cloud** | `AWS S3` | 대용량 파일(이미지, 문서)을 안정적이고 효율적으로 저장하고 관리하기 위해 사용했습니다. |
| **Infra** | `Docker`, `GitHub Actions` | Docker를 통해 개발 환경의 일관성을 유지하고 배포를 자동화했으며, GitHub Actions로 CI/CD 파이프라인을 구축했습니다. |
| **API Docs**| `Swagger (OpenAPI 3.0)` | API 명세를 표준화하고, 팀원 간의 협업 효율을 높이며, API 테스트를 용이하게 하기 위해 도입했습니다. |

---

## 📊 4. 기술적 의사결정 및 상세 설계


### **4-1. 도메인 중심 설계 (DDD) 기반 모델링**

객체지향의 장점을 극대화하고 비즈니스 로직의 복잡성을 관리하기 위해 도메인 중심 설계를 적용했습니다.

#### **① 사용자 도메인: 다형성을 활용한 계층 구조 설계**

* **문제**: 일반 사용자(Client)와 변호사(Lawyer)는 공통 속성(계정 정보, 이름 등)을 공유하지만, 변호사만이 가지는 고유 속성(사무실 정보, 전문 분야 등)이 존재합니다. 이를 효율적으로 모델링할 필요가 있었습니다.
* **해결**: JPA의 **`JOINED` 상속 전략**을 채택하여 공통 속성은 `BaseUser`에, 각자의 고유 속성은 하위 테이블에 저장했습니다. 이를 통해 데이터 정규화와 객체지향적 설계를 모두 만족시켰습니다.
* **패턴 적용**: `getResponseName()`과 같이 공통 로직이지만 하위 클래스에서 다르게 구현되어야 하는 행위는 **템플릿 메서드 패턴**을 활용하여 구현의 일관성을 유지하고 확장성을 확보했습니다.

    > 일반 사용자는 익명성 보장을 위해 `닉네임`을, 변호사는 신뢰도 확보를 위해 `실명`을 반환하도록 강제했습니다.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 기본 DTYPE 컬럼 생성
public abstract class BaseUser {
    // 공통 속성 ...

    // 템플릿 메서드: 하위 클래스가 반드시 구현해야 할 응답용 이름 반환 메서드
    public abstract String getResponseName();

    // 공통 비즈니스 로직
    public boolean isAdmin() { return account.getRole().equals(Role.ROLE_ADMIN); }
}

@Entity
@DiscriminatorValue("client")
public class ClientUser extends BaseUser {
    private String nickname; // 닉네임으로 익명성 보장
    
    @Override
    public String getResponseName() { return nickname; }
}

@Entity
@DiscriminatorValue("lawyer")
public class Lawyer extends BaseUser {
    // 변호사 고유 속성 (경력, 학력, 전문분야 등) ...
    
    @Override
    public String getResponseName() { return getName(); } // 실명 사용
}
```

#### **② 파일 도메인: Factory와 Strategy 패턴을 이용한 확장 가능한 파일 관리**

* **문제**: 프로필 이미지, 변호사 자격증 등 다양한 종류의 파일을 업로드해야 하며, 향후 새로운 파일 타입이 추가될 수 있습니다. 또한, 파일 저장소가 AWS S3 외에 다른 서비스로 변경될 가능성도 고려해야 했습니다.
* **해결**:
    1.  **Factory 패턴**: 파일 타입(`ImageType`)에 따라 적절한 `FileProperty` 자식 객체( `ProfileImageProperty`, `LicenseImageProperty` 등)를 생성하는 `FilePropertyFactory`를 구현하여 객체 생성 로직을 캡슐화했습니다.
    2.  **Strategy 패턴**: 파일 저장 로직을 `FileStorage` 인터페이스로 추상화하고, `AmazonS3FileStorage`를 구현체로 두어 향후 다른 저장소로의 교체가 용이하도록 설계했습니다.

```java
// Strategy Pattern: 파일 저장소의 교체 가능성 대비
public interface FileStorage {
    void store(MultipartFile file, String path);
    void delete(String path);
}

// Factory Pattern: 파일 타입에 따른 객체 생성 로직 분리
public interface FilePropertyFactory {
    FileProperty create(...);
    boolean supports(ImageType imageType);
}
```

### **4-2. 비동기 처리를 통한 AI 서비스 연동 및 성능 최적화**

* **문제**: RAG 기반 AI의 답변 생성에는 약 1~2분의 긴 시간이 소요됩니다. 동기 방식으로 처리할 경우, 사용자는 질문 등록 후 응답을 받기까지 오랜 시간 대기해야 하며 이는 최악의 사용자 경험으로 이어집니다.
* **해결**: Spring의 `@Async`를 활용하여 AI 답변 요청을 비동기 백그라운드 스레드로 위임했습니다.
    1.  사용자가 질문을 등록하면, WAS는 즉시 "등록 완료" 응답을 반환합니다.
    2.  동시에, 별도의 스레드에서 AI 서버로 답변 생성 요청(`RestTemplate`)을 보냅니다.
    3.  AI 서버는 답변 생성이 완료되면 Webhook 등을 통해 WAS에 결과를 전달하고, WAS는 해당 질문에 AI 답변을 저장합니다.
* **기대 효과**:
    * **사용자 경험 향상**: 사용자는 대기 시간 없이 즉각적인 피드백을 받습니다.
    * **장애 격리**: AI 서버의 장애나 성능 저하가 메인 서비스(WAS)에 직접적인 영향을 미치는 것을 방지합니다.
    * **자원 효율성**: 요청을 처리하는 메인 스레드가 블로킹되지 않아 더 많은 사용자 요청을 효율적으로 처리할 수 있습니다.

```java
// 질문 생성과 동시에 AI 서버로 비동기 요청
@Transactional
public QuestionResponse.PostQuestion createQuestion(QuestionRequest.Create request) {
    // 1. 질문을 DB에 먼저 저장
    Question savedQuestion = questionRepository.save(question);
    
    // 2. AI 서버에 비동기적으로 답변 생성 요청 (사용자는 즉시 응답 받음)
    asyncRequestUtils.sendPostAsyncRequest(AIAnswerRequest.from(savedQuestion));
    
    return QuestionResponse.PostQuestion.from(savedQuestion);
}

// 비동기 요청을 처리하는 유틸리티 컴포넌트
@Component
public class AsyncRequestUtils {
    @Async
    public void sendPostAsyncRequest(AIAnswerRequest aiAnswerRequest) {
        // 이 작업은 별도의 스레드에서 실행됨
        restTemplate.postForEntity(url, aiAnswerRequest, Void.class);
    }
}
```

---

## 🚀 5. 개발 과정 및 성과

**기술적 도전과 해결 경험**
* **법률 검색 정확도 향상을 위한 임베딩 모델 파인튜닝**: 일반 임베딩 모델은 법률 용어의 미묘한 의미 차이를 구분하지 못해, 사용자의 질문 의도와 다른 판례를 검색하는 문제가 있었습니다. RAG 시스템의 신뢰성은 정확한 문서 검색에 달려있기에, 법률 도메인에 특화된 임베딩 모델 개발을 목표로 삼았습니다. 고성능의 사전 학습된 임베딩 모델을 선정하고, Open API의 판결문 기반 질의응답 데이터셋으로 미세조정(Fine-tuning)을 진행했습니다. 이 과정을 통해 개발된 특화 모델은 RAG 파이프라인의 '검색' 단계에서 유사 판례 검색 정확도를 향상시켰습니다. 생성 모델에 더 정확한 컨텍스트를 제공함으로써, AI 답변이 실제 판례에 근거하도록 만들어 서비스의 핵심 신뢰도를 확보할 수 있었습니다.

* **유연하고 확장성 있는 시스템을 위한 디자인 패턴 적용**: 프로젝트 규모가 커짐에 따라 코드의 결합도를 낮추고 유지보수성을 높이는 것이 중요했습니다. 이를 위해 객체지향 설계 원칙과 함께 여러 디자인 패턴을 적극적으로 적용했습니다. 예를 들어, 프로필 이미지, 변호사 자격증 등 다양한 파일 처리를 위해 전략(Strategy) 패턴과 팩토리(Factory) 패턴을 조합하여 파일 저장 로직과 객체 생성 로직을 분리했습니다. 또한, 일반 사용자와 변호사 객체의 공통 구조와 개별 행위를 효과적으로 관리하기 위해 템플릿 메서드(Template Method) 패턴을 활용했습니다. 이러한 패턴 적용을 통해 향후 새로운 기능이 추가되더라도 기존 코드를 최소한으로 수정하며 확장할 수 있는 유연한 구조를 완성했습니다.

* **CI/CD 파이프라인 구축**: GitHub Actions를 사용하여 main 브랜치에 코드가 병합될 때마다 자동으로 빌드, 테스트, Docker 이미지 빌드 및 배포가 이루어지는 파이프라인을 구축했습니다. 이를 통해 수동 배포의 실수를 줄이고 개발 생산성을 크게 향상시켰습니다.

### **프로젝트를 통해 얻은 역량**

* **설계 역량**: 도메인 중심 설계와 객체지향 원칙, 디자인 패턴을 실제 프로젝트에 적용하여 유지보수성과 확장성이 높은 코드를 작성하는 능력을 길렀습니다.
* **문제 해결 능력**: 동기/비동기 처리, 실시간 통신 등 복잡한 요구사항에 대해 최적의 기술을 선택하고 발생하는 문제들을 능동적으로 해결하는 경험을 쌓았습니다.
* **AI모델 개발역량**: 임베딩 모델 학습에 사용되는 Contrastive Learning에 대한 이론적인 이해와 실제 모델 학습을 통한 AI 모델 개발 역량을 습득하였습니다. 

### **프로젝트 성과**
> 한국어 법률 정보 검색을 위한 임베딩 모델 [kakao1513/KURE-legal-ft-v1](https://huggingface.co/kakao1513/KURE-legal-ft-v1) 개발
> 한국어 법률 모델 학습을 위한 데이터셋 [kakao1513/AI_HUB_legal_QA_data](https://huggingface.co/datasets/kakao1513/AI_HUB_legal_QA_data) 구축

* Contrastive Learning을 통해 Base Model (nlpai-lab/KURE-v1) 대비 NDCG@5 Score **7.2% 향상**
<img width="882" height="437" alt="image" src="https://github.com/user-attachments/assets/f911a5f3-88c9-4ff6-9746-c85b0b5cb161" />

---

## 🔗 6. 관련 링크

* **GitHub Repository**: [capstone2_BE_WAS](https://github.com/kit-se-capstone2/capstone2_BE_WAS)
* **사용한 AI 모델 (Embedding)**: [Hugging Face - KURE-legal-ft-v1](https://huggingface.co/kakao1513/KURE-legal-ft-v1)
* **API Documentation**: Swagger UI (애플리케이션 실행 후 `/api-docs` 경로에서 확인 가능)

---
