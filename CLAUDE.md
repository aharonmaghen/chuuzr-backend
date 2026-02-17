# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

- **Build**: `./gradlew build`
- **Run**: `./gradlew bootRun`
- **Test**: `./gradlew test`
- **Run single test**: `./gradlew test --tests "com.chuuzr.chuuzrbackend.SomeTestClass.testMethod"`
- **Start infrastructure** (PostgreSQL, Redis): `docker compose up -d db redis`
- **Start infrastructure with GUIs** (+ pgAdmin, RedisInsight): `docker compose up -d db redis pgadmin redisinsight`
- **Start everything** (including app): `docker compose up -d`

Tests and `bootRun` require PostgreSQL and Redis running locally (use Docker Compose).

## Environment Setup

Copy `.env.template` to `.env` and fill in values. Key variables: database credentials, JWT secret/expirations, Twilio SMS credentials, TMDB API key. The app uses `spring-dotenv` to load `.env` automatically.

## Architecture

Spring Boot 3.2 REST API (Java 17) with layered architecture:

- **controller/** - REST endpoints under `/api`, annotated with OpenAPI/Swagger docs
- **service/** - Business logic with `@Transactional` semantics; interfaces with `*Impl` classes
- **repository/** - Spring Data JPA repositories
- **model/** - JPA entities; junction tables (RoomUser, RoomOption, UserVote) use composite keys via `@IdClass`
- **dto/** - Request/response DTOs organized in subdirectories per domain; mapper classes handle entity-DTO conversion
- **security/** - JWT authentication filter, OTP-based login flow (phone + SMS via Twilio)
- **exception/** - Custom exception hierarchy extending `BaseException`, caught by `GlobalExceptionHandler`
- **error/** - `ErrorCode` enum mapping error codes to HTTP statuses
- **util/** - Custom Jakarta Bean Validation validators (phone, email, URL, country code)

## Code Conventions

- **No Lombok** — manual getters, setters, constructors throughout
- **DI**: Constructor injection with `@Autowired`
- **Logging**: `private static final Logger logger = LoggerFactory.getLogger(ClassName.class)` on controllers and services. Use `debug` on method entry, `info` on completion. Never log sensitive data (use `PiiMaskingUtil`).
- **Mappers**: Static methods in `*Mapper` classes (`toResponseDTO`, `toEntity`, `updateEntityFromDTO`) — no MapStruct
- **Services**: `@Transactional` at class level, `@Transactional(readOnly = true)` on read-only methods. Auth services use interface + `*Impl`; domain services are concrete classes.
- **Controllers**: Every endpoint annotated with `@Operation`, `@ApiResponses`, `@Tag`, and `@SecurityRequirement` for OpenAPI docs. POST returns `201` with `Location` header, PUT/GET returns `200`.
- **DTOs**: Organized in subdirectories per domain under `dto/` (e.g., `dto/room/RoomRequestDTO`, `dto/room/RoomResponseDTO`, `dto/room/RoomMapper`)

## Key Patterns

- **Authentication flow**: OTP requested via SMS -> OTP verified -> JWT access token + refresh token (stored in Redis) issued. Two roles: `ROLE_PRE_REGISTER` (post-OTP, pre-profile) and `ROLE_USER` (fully registered).
- **Public identifiers**: Entities use auto-increment `id` internally but expose `uuid` (UUID) fields in APIs.
- **Vote transitions**: Votes go UP/DOWN/NONE with enforced transition rules (must go through NONE).
- **Search providers**: Factory pattern (`SearchProviderFactory`) for extensible external search (currently TMDB for movies).
- **Database migrations**: Flyway with migrations in `src/main/resources/db/migration/`. Hibernate DDL set to `validate` (schema managed by Flyway, not Hibernate).
- **SMS**: Profile-based (`spring.profiles.active`); `MockSmsService` available for local dev.
- **Redis**: Used for OTP storage, refresh token management, and pre-registration flow state.
