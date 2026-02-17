# chuuzr-backend

The backend for **chuuzr** — a versatile decision-making and group voting application. Originally built to help friends vote on which movie to watch, chuuzr has evolved into a platform for group decisions of any kind: choosing restaurants, weekend plans, travel destinations, and more.

## Overview

Spring Boot REST API that powers chuuzr, a social coordination platform built with flexibility and simplicity in mind. Users can create "rooms" with typed options (like movies, restaurants, etc.), invite others, and vote collaboratively.

## Features

- **Rooms** — Create voting rooms scoped to an option type (e.g., "Movie")
- **Options** — Add options manually or search external providers (currently TMDB for movies)
- **Voting** — Upvote/downvote options with enforced state transitions (UP/DOWN must go through NONE)
- **Score tracking** — Real-time aggregated scores per option per room
- **OTP authentication** — Phone-based login via SMS (Twilio) with JWT access tokens and refresh token rotation
- **User profiles** — Name, nickname, phone, profile picture with custom validation
- **External search** — Extensible search provider system (factory pattern) with TMDB integration
- **API documentation** — Full OpenAPI 3 / Swagger UI

## Tech Stack

- **Java 17** / **Spring Boot 3.2**
- **PostgreSQL 15** with **Flyway** migrations
- **Redis 7** (OTP storage, refresh tokens, pre-registration state)
- **Spring Security** + **JWT** (jjwt) + OTP authentication
- **Spring Data JPA** with **Hypersistence Utils** (JSONB support)
- **Twilio** for SMS
- **libphonenumber** for phone validation
- **springdoc-openapi** for API docs
- **Gradle** build system

## Running Locally

### Prerequisites

- Java 17+
- Docker and Docker Compose

### Setup

1. Clone the repository:

```bash
git clone https://github.com/aharonmaghen/chuuzr-backend.git
cd chuuzr-backend
```

2. Copy `.env.template` to `.env` and fill in values:

```bash
cp .env.template .env
```

Key variables: database credentials, JWT secret/expirations, Twilio SMS credentials, TMDB API key. See `.env.template` for the full list.

### Option 1: Docker Compose (Recommended)

Start everything (app + PostgreSQL + Redis):

```bash
docker compose up -d
```

Or start only the infrastructure and run the app locally:

```bash
docker compose up -d db redis
./gradlew bootRun
```

To include database/cache GUIs (pgAdmin on port 5050, RedisInsight on port 5540):

```bash
docker compose up -d db redis pgadmin redisinsight
```

### Option 2: Gradle Only

Ensure PostgreSQL and Redis are running locally, then:

```bash
./gradlew bootRun
```

### Spring Profiles

- **`dev`** — Uses a mock SMS service (no Twilio needed) and relaxes Swagger UI auth. Set `SPRING_PROFILES_ACTIVE=dev` in `.env`.
- **Default (production)** — Uses Twilio for SMS. Swagger UI requires authentication.

## API Documentation

Once the server is running, visit `/swagger-ui/index.html` to explore the API.

### Endpoints

| Area | Base Path | Description |
|------|-----------|-------------|
| Auth | `/api/auth` | OTP request/verify, token refresh, logout |
| Users | `/api/users` | Profile creation and management |
| Rooms | `/api/rooms` | Create and manage voting rooms |
| Room Members | `/api/rooms/{uuid}/users` | Add users to rooms, list members |
| Room Options | `/api/rooms/{uuid}/options` | Add options to rooms, list room options |
| Voting | `/api/rooms/{uuid}/options/{uuid}/votes` | Cast and update votes |
| Options | `/api/options` | CRUD for options |
| Option Types | `/api/option-types` | CRUD for option types |
| Search | `/api/rooms/{uuid}/search` | Search external providers for options |

## Architecture

Layered Spring Boot architecture:

- **controller/** — REST endpoints under `/api` with OpenAPI annotations
- **service/** — Business logic with `@Transactional` semantics
- **repository/** — Spring Data JPA repositories
- **model/** — JPA entities with composite keys for junction tables
- **dto/** — Request/response DTOs organized by domain, with static mapper classes
- **security/** — JWT filter, OTP login flow, Spring Security configuration
- **exception/** — Custom exception hierarchy with centralized error handling
- **util/** — Custom Bean Validation validators (phone, country code, URL, etc.)

## Inspiration

chuuzr began as "Movie Night", a tool to help groups agree on what to watch together. As the concept grew, it became clear that the same voting mechanism could be used for any shared decision.

---

> Built with Java. Inspired by the power of choice.
