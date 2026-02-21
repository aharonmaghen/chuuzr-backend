package com.chuuzr.chuuzrbackend.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.chuuzr.chuuzrbackend.dto.websocket.OptionAddedPayload;
import com.chuuzr.chuuzrbackend.dto.websocket.OptionRemovedPayload;
import com.chuuzr.chuuzrbackend.dto.websocket.RoomEventType;
import com.chuuzr.chuuzrbackend.dto.websocket.RoomUpdatedPayload;
import com.chuuzr.chuuzrbackend.dto.websocket.RoomWebSocketEvent;
import com.chuuzr.chuuzrbackend.dto.websocket.UserJoinedPayload;
import com.chuuzr.chuuzrbackend.dto.websocket.VoteUpdatedPayload;
import com.chuuzr.chuuzrbackend.event.OptionAddedEvent;
import com.chuuzr.chuuzrbackend.event.OptionRemovedEvent;
import com.chuuzr.chuuzrbackend.event.RoomUpdatedEvent;
import com.chuuzr.chuuzrbackend.event.UserJoinedEvent;
import com.chuuzr.chuuzrbackend.event.VoteUpdatedEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private static final String ROOM_TOPIC_PREFIX = "/topic/rooms/";

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onVoteUpdated(VoteUpdatedEvent event) {
        VoteUpdatedPayload payload = new VoteUpdatedPayload(
                event.getOptionUuid(),
                event.getActorUserUuid(),
                event.getVoteType(),
                event.getNewScore());
        broadcast(event.getRoomUuid().toString(), RoomEventType.VOTE_UPDATED, payload);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOptionAdded(OptionAddedEvent event) {
        OptionAddedPayload payload = new OptionAddedPayload(
                event.getOptionUuid(),
                event.getOptionName(),
                event.getImageUrl(),
                event.getScore(),
                event.getCreatedAt());
        broadcast(event.getRoomUuid().toString(), RoomEventType.OPTION_ADDED, payload);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOptionRemoved(OptionRemovedEvent event) {
        OptionRemovedPayload payload = new OptionRemovedPayload(event.getOptionUuid());
        broadcast(event.getRoomUuid().toString(), RoomEventType.OPTION_REMOVED, payload);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRoomUpdated(RoomUpdatedEvent event) {
        RoomUpdatedPayload payload = new RoomUpdatedPayload(
                event.getName(),
                event.getOptionTypeName(),
                event.getUpdatedAt());
        broadcast(event.getRoomUuid().toString(), RoomEventType.ROOM_UPDATED, payload);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserJoined(UserJoinedEvent event) {
        UserJoinedPayload payload = new UserJoinedPayload(
                event.getUserUuid(),
                event.getUserNickname());
        broadcast(event.getRoomUuid().toString(), RoomEventType.USER_JOINED, payload);
    }

    private void broadcast(String roomUuid, RoomEventType eventType, Object payload) {
        String destination = ROOM_TOPIC_PREFIX + roomUuid;
        logger.info("Broadcasting {} to {}", eventType, destination);
        messagingTemplate.convertAndSend(destination, new RoomWebSocketEvent(eventType, payload));
    }
}
