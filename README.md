# chuuzr-backend

The backend for **chuuzr** — a versatile decision-making and group voting application. Originally built to help friends vote on which movie to watch, chuuzr has evolved into a platform for group decisions of any kind: choosing restaurants, weekend plans, travel destinations, and more.

## 🚀 Overview

This is the Spring Boot backend that powers chuuzr, a social coordination platform built with flexibility and simplicity in mind. Users can create "rooms", add customizable "options" (like movies, restaurants, etc.), invite others, and vote collaboratively in real time.

## ✨ Features

- ✅ Create and manage voting rooms
- 📬 Invite friends via shareable links
- 🗳️ Real-time voting on customizable options
- 📊 Aggregated results with winner selection
- 🔒 OTP-based user login (via phone number)
- 🌐 API-first design with OpenAPI documentation (via springdoc)
- 📦 RESTful endpoints with clear structure

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **PostgreSQL**
- **Gradle**
- **springdoc-openapi**
- **JWT / OTP Authentication (Planned or Implemented)**
- **Hosted on:** _coming soon_

## 🧪 Running Locally

First, clone the repository:

```bash
git clone https://github.com/aharonmaghen/chuuzr-backend.git
cd chuuzr-backend
```

### Option 1: Using Docker (Recommended)

Make sure you have Docker and Docker Compose installed. Then run:

```bash
docker-compose up
```

This will start the backend service and a PostgreSQL database using the configuration in `docker-compose.yml`.

### Option 2: Using Gradle

Ensure you have a PostgreSQL database running and update your `application.properties` file accordingly:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/chuuzr_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Then run the application:

```bash
./gradlew bootRun
```

## 📖 API Documentation

Visit `/swagger-ui.html` (or `/swagger-ui/index.html`) once the server is running to explore available endpoints.

## 🔮 Roadmap

- [ ] User profile and authentication enhancements
- [ ] WebSocket support for real-time updates
- [ ] Admin/moderator roles per room
- [ ] Option categories with external APIs (e.g., Yelp, TMDb, etc.)
- [ ] Notifications & reminders

## 🧠 Inspiration

chuuzr began as "Movie Night", a tool to help groups agree on what to watch together. As the concept grew, it became clear that the same voting mechanism could be used for any shared decision.

---

> Built with ❤️ and Java. Inspired by the power of choice.
