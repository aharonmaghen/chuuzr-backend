package com.chuuzr.chuuzrbackend.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class OptionRemovedEvent extends ApplicationEvent {

    private final UUID roomUuid;
    private final UUID optionUuid;

    public OptionRemovedEvent(Object source, UUID roomUuid, UUID optionUuid) {
        super(source);
        this.roomUuid = roomUuid;
        this.optionUuid = optionUuid;
    }

    public UUID getRoomUuid() { return roomUuid; }
    public UUID getOptionUuid() { return optionUuid; }
}
