package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteMapper;
import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
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

  private static final Logger logger = LoggerFactory.getLogger(UserVoteService.class);

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
  public UserVoteSummaryResponseDTO castVote(UUID roomUuid, UUID userUuid, UUID optionUuid, VoteType voteType) {
    logger.debug("Casting vote roomUuid={}, userUuid={}, optionUuid={}, voteType={}", roomUuid, userUuid, optionUuid,
        voteType);
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
      logger.debug("Vote unchanged for userUuid={}, optionUuid={}", userUuid, optionUuid);
      return UserVoteMapper.toSummaryDTO(voteType, roomOption.getScore());
    } else {
      existingUserVote.setVoteType(voteType);
      userVoteRepository.save(existingUserVote);
      int newScore = applyScoreChange(roomOption, oldType, voteType);
      return UserVoteMapper.toSummaryDTO(voteType, newScore);
    }
  }

  private int applyScoreChange(RoomOption option, VoteType oldType, VoteType newType) {
    int delta = voteValue(newType) - voteValue(oldType);
    if (delta != 0) {
      roomOptionRepository.adjustScore(
          option.getRoomOptionId().getRoomId(),
          option.getRoomOptionId().getOptionId(),
          delta);
    }
    int currentScore = option.getScore() != null ? option.getScore() : 0;
    return currentScore + delta;
  }

  private int voteValue(VoteType type) {
    return switch (type) {
      case UP -> 1;
      case DOWN -> -1;
      case NONE -> 0;
    };
  }
}
