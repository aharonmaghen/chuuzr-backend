package com.chuuzr.chuuzrbackend.dto.websocket;

import java.util.UUID;

public class UserJoinedPayload {

    private final UUID userUuid;
    private final String userNickname;

    public UserJoinedPayload(UUID userUuid, String userNickname) {
        this.userUuid = userUuid;
        this.userNickname = userNickname;
    }

    public UUID getUserUuid() { return userUuid; }
    public String getUserNickname() { return userNickname; }
}
