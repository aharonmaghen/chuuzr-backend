package com.chuuzr.chuuzrbackend.dto.websocket;

import java.util.UUID;

import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

public class VoteUpdatedPayload {

    private final UUID optionUuid;
    private final UUID actorUserUuid;
    private final VoteType voteType;
    private final int newScore;

    public VoteUpdatedPayload(UUID optionUuid, UUID actorUserUuid, VoteType voteType, int newScore) {
        this.optionUuid = optionUuid;
        this.actorUserUuid = actorUserUuid;
        this.voteType = voteType;
        this.newScore = newScore;
    }

    public UUID getOptionUuid() { return optionUuid; }
    public UUID getActorUserUuid() { return actorUserUuid; }
    public VoteType getVoteType() { return voteType; }
    public int getNewScore() { return newScore; }
}
