package com.chuuzr.chuuzrbackend.dto.uservote;

import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionMapper;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserMapper;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.UserVote;
import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;

public class UserVoteMapper {

  public static UserVoteResponseDTO toResponseDTO(UserVote userVote) {
    if (userVote == null) {
      return null;
    }
    return new UserVoteResponseDTO(
        RoomUserMapper.toResponseDTO(userVote.getRoomUser()),
        RoomOptionMapper.toResponseDTO(userVote.getRoomOption()),
        userVote.getVoteType(),
        userVote.getCreatedAt(),
        userVote.getUpdatedAt());
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
