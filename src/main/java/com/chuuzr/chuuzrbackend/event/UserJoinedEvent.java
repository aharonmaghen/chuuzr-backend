package com.chuuzr.chuuzrbackend.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

public class UserJoinedEvent extends ApplicationEvent {

    private final UUID roomUuid;
    private final UUID userUuid;
    private final String userNickname;

    public UserJoinedEvent(Object source, UUID roomUuid, UUID userUuid, String userNickname) {
        super(source);
        this.roomUuid = roomUuid;
        this.userUuid = userUuid;
        this.userNickname = userNickname;
    }

    public UUID getRoomUuid() { return roomUuid; }
    public UUID getUserUuid() { return userUuid; }
    public String getUserNickname() { return userNickname; }
}
