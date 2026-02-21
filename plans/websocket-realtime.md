# WebSocket Real-Time Updates

## Context

The Chuuzr backend is a synchronous REST API with no real-time notification capability. This adds STOMP-over-WebSocket so clients receive push events — without polling — when votes change, options are added/removed, the room name changes, or a new user joins.

**Design goals:**
- Single-instance initially (in-process STOMP broker), structured so Redis pub/sub can be added later for multi-instance deployments without touching services or the event listener.
- Events only broadcast after DB transaction commits (`@TransactionalEventListener(AFTER_COMMIT)`).
- Room membership enforced on `SUBSCRIBE` — users only receive events for rooms they have joined.

---

## Architecture

```
Client (SockJS + STOMP.js)
  |  CONNECT  →  StompAuthChannelInterceptor (JWT validation)
  |  SUBSCRIBE /topic/rooms/{uuid}  →  membership check
  |
  ↑  SimpMessagingTemplate.convertAndSend("/topic/rooms/{uuid}", envelope)
  |
WebSocketEventListener  (@TransactionalEventListener AFTER_COMMIT)
  |
ApplicationEventPublisher  (inside @Transactional service methods)
  |
UserVoteService / RoomOptionService / RoomService / RoomUserService
```

**Subscription topic:** `/topic/rooms/{roomUuid}` — single topic per room, all event types delivered here.

**Message envelope:**
```json
{
  "eventType": "VOTE_UPDATED",
  "occurredAt": "2026-02-19T12:34:56.789Z",
  "payload": { ... }
}
```

---

## Files to Create

All base paths: `src/main/java/com/chuuzr/chuuzrbackend/`

| Path | Purpose |
|---|---|
| `config/WebSocketConfig.java` | STOMP endpoint `/ws` (SockJS), simple broker `/topic`, interceptor wiring |
| `security/StompAuthChannelInterceptor.java` | JWT on CONNECT, room membership on SUBSCRIBE |
| `event/VoteUpdatedEvent.java` | roomUuid, optionUuid, actorUserUuid, voteType, newScore |
| `event/OptionAddedEvent.java` | roomUuid, optionUuid, name, imageUrl, score, createdAt |
| `event/OptionRemovedEvent.java` | roomUuid, optionUuid (future-proofing) |
| `event/RoomUpdatedEvent.java` | roomUuid, name, optionTypeName, updatedAt |
| `event/UserJoinedEvent.java` | roomUuid, userUuid, userNickname |
| `dto/websocket/RoomEventType.java` | Enum: VOTE_UPDATED, OPTION_ADDED, OPTION_REMOVED, ROOM_UPDATED, USER_JOINED |
| `dto/websocket/RoomWebSocketEvent.java` | Envelope: eventType, payload, occurredAt |
| `dto/websocket/VoteUpdatedPayload.java` | optionUuid, actorUserUuid, voteType, newScore |
| `dto/websocket/OptionAddedPayload.java` | optionUuid, optionName, imageUrl, score, createdAt |
| `dto/websocket/OptionRemovedPayload.java` | optionUuid |
| `dto/websocket/RoomUpdatedPayload.java` | name, optionTypeName, updatedAt |
| `dto/websocket/UserJoinedPayload.java` | userUuid, userNickname |
| `websocket/WebSocketEventListener.java` | @TransactionalEventListener(AFTER_COMMIT) → SimpMessagingTemplate |

## Files to Modify

| File | Change |
|---|---|
| `build.gradle` | Add `spring-boot-starter-websocket` |
| `security/SecurityConfig.java` | Add `.requestMatchers("/ws/**").permitAll()` before `anyRequest()` |
| `security/DevSecurityConfig.java` | Same `/ws/**` permit |
| `service/UserVoteService.java` | Inject ApplicationEventPublisher; publish VoteUpdatedEvent after applyScoreChange (not when vote unchanged) |
| `service/RoomOptionService.java` | Inject ApplicationEventPublisher; publish OptionAddedEvent after save() |
| `service/RoomService.java` | Inject ApplicationEventPublisher; publish RoomUpdatedEvent after save() in updateRoom() only |
| `service/RoomUserService.java` | Inject ApplicationEventPublisher; publish UserJoinedEvent after save() |

---

## Phase 1 — Foundation ✅

**Goal:** Working (unauthenticated) STOMP endpoint.

1. Add `spring-boot-starter-websocket` to `build.gradle`
2. Create `WebSocketConfig` (STOMP endpoint `/ws` with SockJS, simple broker `/topic`, no interceptor yet)
3. Add `.requestMatchers("/ws/**").permitAll()` to `SecurityConfig` + `DevSecurityConfig`

**Verify:** `./gradlew build` compiles; a STOMP client can connect to `/ws` unauthenticated and subscribe to `/topic/rooms/test`.

---

## Phase 2 — Authentication & Authorization

**Goal:** Secure the WebSocket channel using existing JWT infrastructure.

4. Create `StompAuthChannelInterceptor`
   - **CONNECT:** Extract `Authorization: Bearer {token}` from STOMP headers → `jwtUtil.validateAccessToken()` → role must be `ROLE_USER` → `authService.getInternalUserContext(userUuid)` → `accessor.setUser(auth)`
   - **SUBSCRIBE:** Re-read stored principal; parse room UUID from `/topic/rooms/{roomUuid}`; call `roomUserRepository.findByUuids(roomUuid, userUuid)` → throw `AuthorizationException` if not a member
5. Wire interceptor into `WebSocketConfig.configureClientInboundChannel()`

**Verify:** CONNECT with valid JWT succeeds; without JWT is rejected; SUBSCRIBE to a room the user isn't a member of is rejected with an ERROR frame.

---

## Phase 3 — Event System

**Goal:** Build the broadcasting plumbing; services remain unchanged.

6. Create all 5 `event/` classes (value types only — no JPA entities — because persistence context may be closed at AFTER_COMMIT)
7. Create all 7 `dto/websocket/` classes
8. Create `WebSocketEventListener` with one `@TransactionalEventListener(phase = AFTER_COMMIT)` per event type, each calling `messagingTemplate.convertAndSend("/topic/rooms/{roomUuid}", new RoomWebSocketEvent(...))`

**Verify:** `./gradlew build` compiles cleanly.

---

## Phase 4 — Service Integration

**Goal:** Mutating operations trigger real-time broadcast end-to-end.

9. `UserVoteService` — publish `VoteUpdatedEvent` after `applyScoreChange()` returns (skip unchanged votes)
10. `RoomOptionService` — publish `OptionAddedEvent` after `roomOptionRepository.save()`
11. `RoomService` — publish `RoomUpdatedEvent` after `roomRepository.save()` in `updateRoom()` only (not `createRoom`)
12. `RoomUserService` — publish `UserJoinedEvent` after `roomUserRepository.save()`

**Verify:**
- `./gradlew test` — all existing tests pass
- Cast a vote → `VOTE_UPDATED` arrives on subscription
- Add an option → `OPTION_ADDED`
- Update room name → `ROOM_UPDATED`
- Join room → `USER_JOINED`
- Confirm non-members cannot subscribe (ERROR frame)

---

## Future: Multi-Instance (Redis Pub/Sub)

Extract `WebSocketEventListener.broadcast()` behind a `RoomEventPublisher` interface:
- `LocalRoomEventPublisher` — current behavior (`SimpMessagingTemplate` directly)
- `RedisRoomEventPublisher` — serialize event JSON → `redisTemplate.convertAndSend("room-events:{roomUuid}", json)`
- New `RedisRoomEventSubscriber` component — subscribes to `room-events:*` via `RedisMessageListenerContainer`, calls `SimpMessagingTemplate` on receipt

Services and `StompAuthChannelInterceptor` are **untouched**. Toggle via `@ConditionalOnProperty`.
