package com.chuuzr.chuuzrbackend.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

public class VoteUpdatedEvent extends ApplicationEvent {

    private final UUID roomUuid;
    private final UUID optionUuid;
    private final UUID actorUserUuid;
    private final VoteType voteType;
    private final int newScore;

    public VoteUpdatedEvent(Object source, UUID roomUuid, UUID optionUuid,
            UUID actorUserUuid, VoteType voteType, int newScore) {
        super(source);
        this.roomUuid = roomUuid;
        this.optionUuid = optionUuid;
        this.actorUserUuid = actorUserUuid;
        this.voteType = voteType;
        this.newScore = newScore;
    }

    public UUID getRoomUuid() { return roomUuid; }
    public UUID getOptionUuid() { return optionUuid; }
    public UUID getActorUserUuid() { return actorUserUuid; }
    public VoteType getVoteType() { return voteType; }
    public int getNewScore() { return newScore; }
}
