package com.chuuzr.chuuzrbackend.dto.websocket;

import java.time.Instant;

public class RoomWebSocketEvent {

    private final RoomEventType eventType;
    private final Object payload;
    private final Instant occurredAt;

    public RoomWebSocketEvent(RoomEventType eventType, Object payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.occurredAt = Instant.now();
    }

    public RoomEventType getEventType() { return eventType; }
    public Object getPayload() { return payload; }
    public Instant getOccurredAt() { return occurredAt; }
}
