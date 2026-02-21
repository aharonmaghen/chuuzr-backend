package com.chuuzr.chuuzrbackend.dto.websocket;

import java.time.LocalDateTime;

public class RoomUpdatedPayload {

    private final String name;
    private final String optionTypeName;
    private final LocalDateTime updatedAt;

    public RoomUpdatedPayload(String name, String optionTypeName, LocalDateTime updatedAt) {
        this.name = name;
        this.optionTypeName = optionTypeName;
        this.updatedAt = updatedAt;
    }

    public String getName() { return name; }
    public String getOptionTypeName() { return optionTypeName; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
