# 연가(戀歌) — 시 추천 서비스

글을 쓰면 당신의 감정에 어울리는 시를 추천합니다.

## 서비스 소개

'음악도, 영상도 소비하는 방식이 바뀌었는데 왜 시집을 사는 방식은 그대로인가'에서 출발한 서비스입니다.

사용자가 일기를 쓰거나 좋아하는 글을 아카이빙하면, 감정 태그를 기반으로 어울리는 시를 추천하고, 해당 시가 수록된 시집의 구매 페이지로 연결합니다.

## 핵심 기능

- **글 작성/아카이빙** — 일기(DIARY)와 좋아하는 글(QUOTE) 두 가지 타입
- **감정 태그 기반 시 추천** — 10개 감정 태그(사랑, 외로움, 슬픔, 그리움, 불안, 희망, 절망, 설렘, 허무, 후회) 매칭
- **시집 구매 연결** — 추천된 시 → 시집 상세 → 외부 구매 페이지

## 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x, Spring MVC |
| Template | Thymeleaf + Layout Dialect |
| ORM | Spring Data JPA |
| Database | H2 (개발) |
| Build | Gradle (Groovy) |

## 아키텍처

```
Controller (요청 처리)
    ↕ DTO
Service (비즈니스 로직)
    ↕
Repository (데이터 접근)
    ↕
H2 Database
```

- **3계층 분리**: Controller → Service → Repository
- **DTO 패턴**: 엔티티를 화면에 직접 노출하지 않고, 데이터 전달 전용 객체로 변환하여 계층 간 의존성 분리

## 도메인 설계

```
Writing (글)
├── id, content, type(DIARY/QUOTE), tags, createdAt, updatedAt

Poem (시)
├── id, title, author, content, tags, bookId(FK)

Book (시집)
├── id, title, author, publishedYear, purchaseUrl, coverImageUrl
```

- Writing → Poem: 태그 겹침 수 기반 추천 (정렬 + 랜덤 셔플)
- Poem → Book: 다대일 관계 (하나의 시집에 여러 시)

## 실행 방법

```bash
git clone https://github.com/brocolies/poem.git
cd poem
./gradlew bootRun
```

`http://localhost:8080` 접속

## 로드맵

- [x] 글 CRUD (일기/아카이빙)
- [x] 태그 기반 시 추천
- [x] 시집 상세 페이지 + 외부 구매 링크
- [x] DTO 패턴 적용
- [ ] Claude API 연동 (감정 자동 분석)
- [ ] 오늘의 시 기능
- [ ] Spring Security (회원가입/로그인)
- [ ] PostgreSQL 전환 + 배포
