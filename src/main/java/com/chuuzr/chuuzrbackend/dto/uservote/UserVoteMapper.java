package com.chuuzr.chuuzrbackend.dto.uservote;

import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.UserVote;
import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;
import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;

public class UserVoteMapper {

  public static UserVoteSummaryResponseDTO toSummaryDTO(VoteType voteType, Integer score) {
    return new UserVoteSummaryResponseDTO(voteType, score);
  }

  public static UserVote toEntity(RoomUser roomUser, RoomOption roomOption, UserVote.VoteType voteType) {
    UserVote userVote = new UserVote();
    userVote.setUserVoteId(new UserVoteId(roomUser.getRoomUserId().getRoomId(), roomUser.getRoomUserId().getUserId(),
        roomOption.getRoomOptionId().getOptionId()));
    userVote.setRoomUser(roomUser);
    userVote.setRoomOption(roomOption);
    userVote.setVoteType(voteType);
    return userVote;
  }
}
