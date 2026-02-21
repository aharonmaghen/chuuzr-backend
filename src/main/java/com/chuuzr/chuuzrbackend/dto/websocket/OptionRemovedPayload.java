package com.chuuzr.chuuzrbackend.dto.websocket;

import java.util.UUID;

public class OptionRemovedPayload {

    private final UUID optionUuid;

    public OptionRemovedPayload(UUID optionUuid) {
        this.optionUuid = optionUuid;
    }

    public UUID getOptionUuid() { return optionUuid; }
}
