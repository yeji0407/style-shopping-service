# Style Shopping Service


## 실행 방법

### ☑️ 한번에 실행

```bash
docker run -p 8080:8080 yeji0407/style-shopping-service
```
[화면접근](http://localhost:8080/)

### ☑️ docker-compose를 통한 빌드 + 실행

```bash
docker compose up --build
```
[화면접근](http://localhost:8080/)

---

## 필요 요건 및 API 매핑

### 1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
- `GET /api/product/lowest-prices`
```bash
curl -X GET http://localhost:8080/api/product/lowest-prices
```

### 2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
- `GET /api/product/brand-lowest-prices`
```bash
curl -X GET http://localhost:8080/api/product/brand-lowest-prices
```

### 3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
- `POST /api/product/category-price-range`
  - Body: `{ "categoryName": "상의" }`
```bash
curl -X POST http://localhost:8080/api/product/category-price-range -H "Content-Type: application/json" -d '{"categoryName": "상의"}'
```

### 4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.
#### 4-1. 브랜드 관리
- `GET /api/brand/list`
```bash
curl -X GET http://localhost:8080/api/brand/list
```

- `POST /api/brand/create`
```bash
curl -X POST http://localhost:8080/api/brand/create -H "Content-Type: application/json" -d '{"name": "newBrand"}'
```

- `POST /api/brand/update/{brandId}`
```bash
curl -X POST http://localhost:8080/api/brand/update/1 -H "Content-Type: application/json" -d '{"name": "updatedBrand"}'
```

- `POST /api/brand/delete`
```bash
curl -X POST http://localhost:8080/api/brand/delete/1
```

#### 4-2. 상품 관리
- `GET /api/product/list`
```bash
curl -X GET http://localhost:8080/api/product/list
```
- `POST /api/product/create`
```bash
curl -X POST http://localhost:8080/api/product/create -H "Content-Type: application/json" -d '{"brandId": 2, "categoryId": 1, "price": 10000}'
```
- `POST /api/product/update/{productId}`
```bash
curl -X POST http://localhost:8080/api/product/update/1 -H "Content-Type: application/json" -d '{"brandId": 2, "categoryId": 1, "price": 20000}'
```
- `POST /api/product/delete`
```bash
curl -X POST http://localhost:8080/api/product/delete/1
```
---

## 테스트 실행 방법
```bash
./mvnw test
```
---

## 사용 기술 스택

### 백엔드
- Spring Boot 3.4.2
- Java 21
- JPA
- QueryDSL
- H2 DB
- Caffeine Cache (로컬 캐시)

#### 테스트
- JUnit 5
- Mockito
- AssertJ

### 프론트엔드
- React
- TypeScript
- Vite
- Ant Design
- Axios

---

## 에러 처리 형식

- 예외는 `BusinessException` 형식으로 처리

### 응답 형식 통일 (CommonResponse)
```json
{
  "success": false,
  "data": null,
  "code": "COMMON_404",
  "message": "요청하신 데이터를 찾을 수 없습니다.",
  "errors": [ { "field": "categoryName", "message": "가능한 값이 아닐 경우..." } ]
}
```

### 오류 코드 목록
- `COMMON_400` 잘못된 요청
- `COMMON_404` 데이터 조회되지 않음
- `COMMON_500` 서버 내부 오류
- ...

---

## 캐시 적용 구조

- `CacheSpec` enum을 통해 캐시 이름, TTL, 최대 개수를 설정
- `@EnumCacheable` : 캐시 저장을 CacheSpec 으로 관리
- `@EnumCacheEvict` : 캐시 해제를 CacheSpec 으로 관리. `allEntries = true`로 모두 삭제 가능

---

## 패키지 구조

```
com.musinsa.style.shopping.service
├── product                 # 상품 도메인
│   ├── web                 # REST API Controller, 요청 파싱 및 응답 방향 지정
│   ├── service             # 비즈니스 로직을 담당
│   ├── message             # API 요청/응답 DTO
│   ├── dto                 # 데이터 조합용 internal DTO
├── brand                   # 브랜드 도메인 (하위 구조는 상품 도메인과 동일)
├── category                # 카테고리 도메인 (하위 구조는 상품 도메인과 동일)
├── common
│   ├── config              # 설정와 캐시 관리
│   ├── exception           # 예외 관리 및 공통 처리
│   ├── message         
│   ├   └── CommonResponse  # 공통 응답 형식
│   ├── code            
│   ├   └── ErrorCode       # 에러 코드
│   ├── util                # 공통 기능 함수
│   └── persistence.jpa     # JPA Entity, Repository
└── StyleShoppingServiceApplication.java
```

```
frontend/src
├── components      # 재사용 가능한 컴포넌트 (폼, 테이블 등)
├── pages           # 실제 화면 단위
├── layout          # 전체 레이아웃
├── hooks           # 커스텀 훅
├── types           # 타입 정의
└── utils           # API 호출 등 도구
```

