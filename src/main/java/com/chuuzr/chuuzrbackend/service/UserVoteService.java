package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteMapper;
import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.InvalidVoteTransitionException;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.UserVote;
import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;
import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomUserRepository;
import com.chuuzr.chuuzrbackend.repository.UserVoteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserVoteService {

  private final UserVoteRepository userVoteRepository;
  private final RoomUserRepository roomUserRepository;
  private final RoomOptionRepository roomOptionRepository;

  @Autowired
  public UserVoteService(UserVoteRepository userVoteRepository, RoomUserRepository roomUserRepository,
      RoomOptionRepository roomOptionRepository) {
    this.userVoteRepository = userVoteRepository;
    this.roomUserRepository = roomUserRepository;
    this.roomOptionRepository = roomOptionRepository;
  }

  @Transactional
  public UserVoteResponseDTO castVote(UUID roomUuid, UUID userUuid, UUID optionUuid, VoteType voteType) {
    RoomUser roomUser = roomUserRepository.findByUuids(roomUuid, userUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_USER_NOT_FOUND,
            "User with UUID " + userUuid + " not found in room " + roomUuid));

    RoomOption roomOption = roomOptionRepository.findByUuids(roomUuid, optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found in room " + roomUuid));

    UserVoteId userVoteId = new UserVoteId(
        roomUser.getRoomUserId().getRoomId(),
        roomUser.getRoomUserId().getUserId(),
        roomOption.getRoomOptionId().getOptionId());

    UserVote existingUserVote = userVoteRepository.findById(userVoteId)
        .orElseGet(() -> UserVoteMapper.toEntity(roomUser, roomOption, VoteType.NONE));
    VoteType oldType = existingUserVote.getVoteType();

    if (oldType == voteType) {
      return UserVoteMapper.toResponseDTO(existingUserVote);
    } else if (oldType != VoteType.NONE && voteType != VoteType.NONE) {
      throw new InvalidVoteTransitionException(
          "Invalid vote transition from " + oldType + " to " + voteType + ". Must transition through NONE.");
    } else {
      existingUserVote.setVoteType(voteType);
      existingUserVote = userVoteRepository.save(existingUserVote);
      applyScoreChange(roomOption, oldType, voteType);
    }

    return UserVoteMapper.toResponseDTO(existingUserVote);
  }

  private void applyScoreChange(RoomOption option, VoteType oldType, VoteType newType) {
    long roomId = option.getRoomOptionId().getRoomId();
    long optId = option.getRoomOptionId().getOptionId();

    if (newType == VoteType.UP || oldType == VoteType.DOWN) {
      roomOptionRepository.incrementScore(roomId, optId);
    } else if (newType == VoteType.DOWN || oldType == VoteType.UP) {
      roomOptionRepository.decrementScore(roomId, optId);
    }
  }
}
