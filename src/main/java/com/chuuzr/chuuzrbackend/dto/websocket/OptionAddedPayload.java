package com.chuuzr.chuuzrbackend.dto.websocket;

import java.time.LocalDateTime;
import java.util.UUID;

public class OptionAddedPayload {

    private final UUID optionUuid;
    private final String optionName;
    private final String imageUrl;
    private final int score;
    private final LocalDateTime createdAt;

    public OptionAddedPayload(UUID optionUuid, String optionName, String imageUrl,
            int score, LocalDateTime createdAt) {
        this.optionUuid = optionUuid;
        this.optionName = optionName;
        this.imageUrl = imageUrl;
        this.score = score;
        this.createdAt = createdAt;
    }

    public UUID getOptionUuid() { return optionUuid; }
    public String getOptionName() { return optionName; }
    public String getImageUrl() { return imageUrl; }
    public int getScore() { return score; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
