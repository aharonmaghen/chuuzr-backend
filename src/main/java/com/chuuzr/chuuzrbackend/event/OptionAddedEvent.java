package com.chuuzr.chuuzrbackend.event;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class OptionAddedEvent extends ApplicationEvent {

    private final UUID roomUuid;
    private final UUID optionUuid;
    private final String optionName;
    private final String imageUrl;
    private final int score;
    private final LocalDateTime createdAt;

    public OptionAddedEvent(Object source, UUID roomUuid, UUID optionUuid,
            String optionName, String imageUrl, int score, LocalDateTime createdAt) {
        super(source);
        this.roomUuid = roomUuid;
        this.optionUuid = optionUuid;
        this.optionName = optionName;
        this.imageUrl = imageUrl;
        this.score = score;
        this.createdAt = createdAt;
    }

    public UUID getRoomUuid() { return roomUuid; }
    public UUID getOptionUuid() { return optionUuid; }
    public String getOptionName() { return optionName; }
    public String getImageUrl() { return imageUrl; }
    public int getScore() { return score; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
