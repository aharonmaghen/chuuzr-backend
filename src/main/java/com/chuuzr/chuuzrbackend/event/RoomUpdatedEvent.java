package com.chuuzr.chuuzrbackend.event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class RoomUpdatedEvent extends ApplicationEvent {

    private final UUID roomUuid;
    private final String name;
    private final String optionTypeName;
    private final LocalDateTime updatedAt;

    public RoomUpdatedEvent(Object source, UUID roomUuid, String name,
            String optionTypeName, LocalDateTime updatedAt) {
        super(source);
        this.roomUuid = roomUuid;
        this.name = name;
        this.optionTypeName = optionTypeName;
        this.updatedAt = updatedAt;
    }

    public UUID getRoomUuid() { return roomUuid; }
    public String getName() { return name; }
    public String getOptionTypeName() { return optionTypeName; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
