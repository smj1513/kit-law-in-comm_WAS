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

## 📊 도메인 모델 및 데이터베이스 설계

### 🏛️ 도메인 중심 설계 (Domain-Driven Design)

#### 1. 사용자 도메인 - 다형성을 활용한 계층 구조

**설계 의도**: 법률 상담 플랫폼의 핵심은 서로 다른 역할을 가진 사용자들입니다. 일반 사용자와 변호사는 공통점도 많지만 고유한 특성도 가져야 합니다.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class BaseUser {
    // 공통 속성: 이름, 생년월일, 전화번호, 계정정보, 프로필이미지
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Account account;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER) 
    private ProfileImageProperty profileImage;
    
    // 템플릿 메서드 패턴 - 하위 클래스에서 구현 강제
    abstract public String getNickname();
    abstract public String getResponseName();
    
    // 공통 비즈니스 로직
    public ChatRoom createChat(BaseUser baseUser) { /* ... */ }
    public boolean isAdmin() { return account.getRole().equals(Role.ROLE_ADMIN); }
}

// 일반 사용자 - 익명성 보장
@Entity
@DiscriminatorValue("client")
public class ClientUser extends BaseUser {
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}")
    private String nickname; // 닉네임으로 익명성 보장
    
    @Override
    public String getResponseName() { return nickname; }
}

// 변호사 - 신뢰성과 전문성 강조
@Entity  
@DiscriminatorValue("lawyer")
public class Lawyer extends BaseUser {
    private String description;
    
    @OneToOne(mappedBy = "lawyer", cascade = CascadeType.ALL)
    private OfficeInfo officeInfo; // 사무실 정보
    
    @OneToOne(mappedBy = "lawyer", cascade = CascadeType.ALL)
    private LicenseImageProperty license; // 자격증 이미지
    
    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL)
    private List<LegalSpecialityInfo> legalSpecialities; // 전문분야
    
    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL)
    private List<Career> careers; // 경력사항
    
    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL)
    private List<Education> educations; // 학력사항
    
    @Override
    public String getResponseName() { return getName(); } // 실명 사용
}
```

**설계 결정의 배경**:
1. **JOINED 상속 전략**: 정규화를 통한 데이터 일관성 확보 + 성능 최적화
2. **템플릿 메서드 패턴**: 공통 로직은 부모에서, 차별화된 로직은 자식에서
3. **익명성 vs 신뢰성**: 일반 사용자는 닉네임, 변호사는 실명 사용으로 플랫폼 특성 반영

#### 2. 질문-답변 도메인 - 비즈니스 규칙의 명확한 표현

**핵심 비즈니스 규칙**:
- 하나의 질문에 여러 답변 가능 (1:N)
- 질문은 반드시 법률 분야로 분류되어야 함
- 답변은 변호사만 작성 가능 (AI 답변은 별도 처리)
- 익명 질문 지원으로 사용자 프라이버시 보호

```java
@Entity
public class Question extends BaseTime {
    @Column(columnDefinition = "TEXT")
    private String title;
    
    @Column(columnDefinition = "TEXT") 
    private String content;
    
    private LocalDate firstOccurrenceDate; // 사건 발생일
    private Boolean isAnonymous; // 익명 여부
    private int viewCount; // 조회수 - 인기도 측정
    
    @Enumerated(EnumType.STRING)
    private LegalSpeciality legalSpeciality; // 법률 분야 필수 분류
    
    @ManyToOne
    @NotNull
    private BaseUser author; // 작성자 (일반사용자만 가능)
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers; // 변호사 답변들
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionReport> reports; // 신고 시스템
    
    // 성능 최적화를 위한 계산 필드
    @Formula("(select count(*) from question_report qr where qr.question_id = id)")
    private int reportsCount;
    
    // 비즈니스 로직 캡슐화
    public void addViewCount() { this.viewCount++; }
    public void addAnswer(Answer answer) { /* 양방향 연관관계 설정 */ }
}

@Entity
public class Answer extends BaseTime {
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Lawyer author; // 변호사만 답변 가능
    
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;
    
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerReport> answerReports;
    
    @Formula("(select count(*) from answer_report ar where ar.answer_id = id)")
    private int reportsCount; // 신고 횟수로 품질 관리
}
```

**설계의 핵심 포인트**:
1. **도메인 무결성**: `@NotNull`, `@Enumerated` 등으로 데이터 일관성 보장
2. **성능 최적화**: `@Formula`로 집계 데이터를 계산 필드로 처리
3. **CASCADE 전략**: 부모 삭제 시 자식 데이터의 자동 정리
4. **비즈니스 로직 캡슐화**: 도메인 객체 내부에 비즈니스 규칙 구현

#### 3. 실시간 채팅 도메인 - 복잡한 상태 관리

**설계 과제**: 1:1 채팅에서 읽음 상태, 마지막 메시지, 참여자 관리 등 복잡한 상태를 효율적으로 처리해야 함

```java
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "participants_constraint", 
        columnNames = {"creator_id", "participant_id"}
    ) // 중복 채팅방 방지
})
public class ChatRoom extends BaseTime {
    @ManyToOne
    private BaseUser creator;    // 채팅 개설자
    
    @ManyToOne  
    private BaseUser participant; // 채팅 참여자
    
    @OneToOne(fetch = FetchType.LAZY)
    private ChatMessage lastMessage; // 마지막 메시지 캐싱
    
    private LocalDateTime lastMessageAt; // 정렬용 시간
    
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;
    
    // 성능 최적화된 읽지 않은 메시지 개수
    @Formula("(select count(*) from chat_message cm where cm.chat_room_id = id and cm.is_read = false)")
    private int unreadMessageCount;
    
    // 비즈니스 로직: 상대방 찾기
    public BaseUser getOtherPerson(BaseUser pivot) {
        return creator.getId().equals(pivot.getId()) ? participant : creator;
    }
    
    // 마지막 메시지 업데이트 로직
    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessage.getSentAt();
    }
}

@Entity
public class ChatMessage extends BaseTime {
    private String message;
    private boolean isRead; // 읽음 상태
    private LocalDateTime sentAt;
    
    @ManyToOne
    private BaseUser sender;
    
    @ManyToOne
    private ChatRoom chatRoom;
    
    // 읽음 처리 비즈니스 로직
    public void readFrom(BaseUser reader) {
        if (!sender.equals(reader)) { // 본인이 보낸 메시지가 아닐 때만
            this.isRead = true;
        }
    }
}
```

**설계의 복잡성 해결**:
1. **유니크 제약조건**: 동일한 사용자 간 중복 채팅방 방지
2. **읽음 상태 관리**: 개별 메시지별 읽음 상태 + 집계를 통한 전체 개수
3. **성능 고려**: `@Formula`와 `FetchType.LAZY`로 불필요한 조회 최소화
4. **비즈니스 로직 캡슐화**: 도메인 객체가 자신의 상태를 관리

#### 4. 신고 시스템 도메인 - 확장 가능한 추상화

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Report extends BaseTime {
    private String reason; // 신고 사유
    
    @ManyToOne
    private BaseUser reporter; // 신고자
}

@Entity
@DiscriminatorValue("question")
public class QuestionReport extends Report {
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;
}

@Entity  
@DiscriminatorValue("answer")
public class AnswerReport extends Report {
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE) 
    private Answer answer;
}
```

**설계 의도**: 향후 댓글, 채팅 메시지 등 다양한 콘텐츠 신고가 추가될 가능성을 고려한 확장 가능한 구조

### 🎯 법률 분야 전문화 설계

#### 세분화된 법률 분야 분류 시스템

```java
@Getter
public enum LegalSpeciality {
    // 형사 분야 - 세부 전문 영역별 분류
    SEXUAL_CRIMES("성범죄"),
    PROPERTY_CRIMES_EMBEZZLEMENT("횡령/배임"),
    PROPERTY_CRIMES_FRAUD("사기/공갈"),
    TRAFFIC_ACCIDENT_HIT_RUN("교통사고/도주"),
    CRIMINAL_PROCEDURE_INVESTIGATION("수사/체포/구속"),
    
    // 민사 분야 - 실생활 밀접 분야
    REAL_ESTATE_GENERAL("부동산 일반"),
    REAL_ESTATE_LEASE("임대차"),
    FINANCIAL_COMPENSATION("손해배상"),
    FAMILY_DIVORCE("이혼"),
    FAMILY_INHERITANCE("상속"),
    
    // 전문 분야
    CORPORATE_LAW("기업법무"),
    IT_IP_FINANCE("IT/지식재산/금융");
    
    private final String description;
    
    // 한국어 검색 지원
    public static Optional<LegalSpeciality> fromDescription(String description) {
        return Arrays.stream(values())
            .filter(field -> field.description.equals(description))
            .findFirst();
    }
}
```

**설계 철학**: 
- 실제 법률 상담에서 자주 발생하는 영역을 세분화
- 변호사의 전문성과 사용자의 니즈를 정확히 매칭
- 한국 법률 체계에 특화된 분류

#### 변호사 전문성 검증 시스템

```java
@Entity
public class LegalSpecialityInfo {
    @Enumerated(EnumType.STRING)
    private LegalSpeciality legalSpeciality;
    
    @ManyToOne
    private Lawyer lawyer;
}

// 변호사 등록 시 전문분야, 경력, 학력 종합 검증
public LawyerRes joinLawyer(JoinLawyer request, MultipartFile licenseImage) {
    Lawyer lawyer = lawyerService.createLawyerLicense(
        Lawyer.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build(),
        licenseImage // 자격증 이미지 필수
    );
    
    // 전문분야 등록
    List<LegalSpecialityInfo> legalSpecialityInfos = 
        request.getLegalSpecialties().stream()
            .map(spec -> LegalSpecialityInfo.builder()
                .legalSpeciality(spec)
                .lawyer(lawyer)
                .build())
            .toList();
    
    // 경력/학력 정보 등록  
    List<Career> careers = /* 경력 정보 매핑 */;
    List<Education> educations = /* 학력 정보 매핑 */;
    
    // 관리자 승인 대기 상태로 설정
    account.setApprovalStatus(ApprovalStatus.WAITING);
}
```

### 🔄 AI 답변 시스템 - 마이크로서비스 연동 설계

**설계 과제**: 긴 AI 처리 시간으로 인한 사용자 경험 저하 방지

```java
// 비동기 AI 답변 요청 시스템
@Component
public class AsyncRequestUtils {
    @Async
    public void sendPostAsyncRequest(AIAnswerRequest aiAnswerRequest) {
        restTemplate.postForEntity(url, aiAnswerRequest, Void.class);
    }
}

// 질문 생성과 동시에 AI 서버로 비동기 요청
public QuestionResponse.PostQuestion createQuestion(QuestionRequest.Create request) {
    Question save = questionRepository.save(question);
    
    // 질문 저장 완료 후 즉시 AI 서버로 비동기 요청
    asyncRequestUtils.sendPostAsyncRequest(AIAnswerRequest.builder()
        .id(save.getId())
        .title(save.getTitle()) 
        .content(save.getContent())
        .build());
        
    return QuestionResponse.PostQuestion.from(save);
}
```

**핵심 설계 원칙**:
1. **응답성 우선**: 사용자는 즉시 질문 등록 완료 응답 받음
2. **장애 격리**: AI 서버 장애가 메인 서비스에 영향 없음
3. **확장성**: AI 서버를 독립적으로 스케일링 가능

### 📈 성능 최적화 설계

#### 1. 지연 로딩과 즉시 로딩 전략
```java
@OneToOne(fetch = FetchType.EAGER)  // 항상 필요한 데이터
private Account account;

@OneToMany(fetch = FetchType.LAZY)  // 필요시에만 로딩  
private List<Question> questions;

@Formula("(select count(*) from question_report qr where qr.question_id = id)")
private int reportsCount; // 집계 데이터를 계산 필드로 처리
```

#### 2. 캐싱 전략
```java
@Entity
@DynamicUpdate // 변경된 필드만 UPDATE 쿼리에 포함
public class ChatRoom extends BaseTime {
    @OneToOne(fetch = FetchType.LAZY)
    private ChatMessage lastMessage; // 마지막 메시지 캐싱으로 조회 성능 향상
}
```

### 🛡️ 데이터 무결성 보장 설계

#### 1. 제약조건을 통한 비즈니스 규칙 강제
```java
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "participants_constraint",
        columnNames = {"creator_id", "participant_id"}
    ) // 중복 채팅방 방지
})

@Column(unique = true)
private String phoneNumber; // 사용자별 고유 전화번호

@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}")
private String nickname; // 닉네임 형식 검증
```

#### 2. 연관관계 라이프사이클 관리
```java
@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Answer> answers; // 질문 삭제 시 답변들도 함께 삭제

@OnDelete(action = OnDeleteAction.CASCADE)
private Question question; // 데이터베이스 레벨에서도 참조 무결성 보장

@PreRemove
public void clear() {
    reports.clear();
    answers.clear(); // 삭제 전 연관관계 정리
}
```

이러한 설계는 **법률 상담 플랫폼의 복잡한 비즈니스 요구사항**을 체계적으로 해결하면서도 **확장성과 유지보수성**을 동시에 확보한 아키텍처입니다.

---

## 🔧 주요 기능 구현

### 🤖 AI 답변 자동 생성 (비동기 처리)
```java
// 질문 생성 시 AI 서버로 비동기 요청
@Async
public void sendPostAsyncRequest(AIAnswerRequest aiAnswerRequest){
    restTemplate.postForEntity(url, aiAnswerRequest, Void.class);
}

// 질문 생성과 동시에 비동기 AI 답변 요청
@EnableAsync  // WebConfig에서 비동기 처리 활성화
public QuestionResponse.PostQuestion createQuestion(QuestionRequest.Create request) {
    Question save = questionRepository.save(question);
    asyncRequestUtils.sendPostAsyncRequest(AIAnswerRequest.builder()
        .id(save.getId())
        .title(save.getTitle())
        .content(save.getContent())
        .build());
    return QuestionResponse.PostQuestion.from(save);
}
```

### 📁 파일 업로드 시스템 (Factory 패턴 + Strategy 패턴)
```java
// 파일 타입별 팩토리 패턴 적용
public interface FilePropertyFactory {
    FileProperty create(String originFileName, String saveFileName, String path, 
                       String contentType, Long size, BaseUser uploader, ImageType type);
    boolean supports(ImageType imageType);
}

// 프로바이더를 통한 팩토리 선택
@Component
public class FilePropertyProvider {
    private final List<FilePropertyFactory> filePropertyFactories;
    
    public FileProperty create(String fileName, String contentType, Long size, 
                              BaseUser uploader, ImageType imageType) {
        for (FilePropertyFactory factory : filePropertyFactories) {
            if (factory.supports(imageType)) {
                return factory.create(fileName, savedFile, path, contentType, size, uploader, imageType);
            }
        }
        throw new BusinessLogicException(ErrorCode.FILE_TYPE_UNSUPPORTED);
    }
}

// AWS S3 저장소 추상화 (Strategy 패턴)
public interface FileStorage {
    void store(MultipartFile file, String path);
    void delete(String path);
}

@Component
public class AmazonS3FileStorage implements FileStorage {
    // S3 구체적 구현
}
```

### 🌐 WebSocket 실시간 채팅 (Chain of Responsibility 패턴)
```java
// WebSocket 명령어 처리를 위한 체인 패턴
public interface CommandHandler {
    void handle(StompHeaderAccessor accessor, StompCommand command);
    boolean supports(StompCommand command);
}

@Component
public class StompInterceptor implements ChannelInterceptor {
    private final List<CommandHandler> commandHandlers;
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompCommand command = accessor.getCommand();
        for (CommandHandler commandHandler : commandHandlers) {
            if (commandHandler.supports(command)) {
                commandHandler.handle(accessor, command);
                break; // 적절한 핸들러가 처리하면 체인 종료
            }
        }
        return message;
    }
}

@MessageMapping("/chat/{chatRoomId}")
public void sendMessage(@RequestBody ChatRequest.ChatMessageReq request,
                       @DestinationVariable Long chatRoomId,
                       Principal principal) {
    chatService.sendMessage(request, chatRoomId, principal);
}
```

### 👥 사용자 계층 구조 (Template Method 패턴 + 상속 구조)
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class BaseUser {
    // 공통 필드와 메서드
    
    // 템플릿 메서드 - 하위 클래스에서 구현 강제
    abstract public String getNickname();
    abstract public String getResponseName();
    
    // 공통 비즈니스 로직
    public ChatRoom createChat(BaseUser baseUser) {
        return ChatRoom.builder()
            .creator(this)
            .participant(baseUser)
            .build();
    }
}

@Entity
@DiscriminatorValue("client")
public class ClientUser extends BaseUser {
    private String nickname;
    
    @Override
    public String getResponseName() {
        return nickname; // 일반 사용자는 닉네임 반환
    }
}

@Entity
@DiscriminatorValue("lawyer")
public class Lawyer extends BaseUser {
    private String realName;
    
    @Override
    public String getResponseName() {
        return realName; // 변호사는 실명 반환
    }
}
```

### 🔒 JWT 기반 보안 (Security Filter Chain)
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

### 📊 파일 속성 상속 구조 (Inheritance + Abstract Factory)
```java
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FileProperty extends BaseTime {
    // 공통 파일 속성
    private String originFileName;
    private String savedFileName;
    private String contentType;
    private Long size;
    private String path;
    
    public abstract void clear(); // 연관관계 해제 로직을 하위 클래스에서 구현
}

@Entity
public class ProfileImageProperty extends FileProperty {
    @OneToOne
    private BaseUser user;
    
    @Override
    public void clear() {
        if (user != null) {
            user.setProfileImage(null);
            this.user = null;
        }
    }
}

@Entity
public class LicenseImageProperty extends FileProperty {
    @ManyToOne
    private Lawyer lawyer;
    
    @Override
    public void clear() {
        if (lawyer != null) {
            lawyer.getLicenseImages().remove(this);
            this.lawyer = null;
        }
    }
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
