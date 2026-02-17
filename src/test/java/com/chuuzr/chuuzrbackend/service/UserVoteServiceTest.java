package com.chuuzr.chuuzrbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteResponseDTO;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.UserVote;
import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;
import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomUserRepository;
import com.chuuzr.chuuzrbackend.repository.UserVoteRepository;

@ExtendWith(MockitoExtension.class)
class UserVoteServiceTest {

  @Mock
  private UserVoteRepository userVoteRepository;

  @Mock
  private RoomUserRepository roomUserRepository;

  @Mock
  private RoomOptionRepository roomOptionRepository;

  @InjectMocks
  private UserVoteService userVoteService;

  private UUID roomUuid;
  private UUID userUuid;
  private UUID optionUuid;
  private RoomUser roomUser;
  private RoomOption roomOption;

  @BeforeEach
  void setUp() {
    roomUuid = UUID.randomUUID();
    userUuid = UUID.randomUUID();
    optionUuid = UUID.randomUUID();

    roomUser = new RoomUser();
    roomUser.getRoomUserId().setRoomId(1L);
    roomUser.getRoomUserId().setUserId(1L);

    roomOption = new RoomOption();
    roomOption.getRoomOptionId().setRoomId(1L);
    roomOption.getRoomOptionId().setOptionId(1L);
  }

  private void stubLookups() {
    when(roomUserRepository.findByUuids(roomUuid, userUuid)).thenReturn(Optional.of(roomUser));
    when(roomOptionRepository.findByUuids(roomUuid, optionUuid)).thenReturn(Optional.of(roomOption));
  }

  @Test
  void castVote_newVoteUp_incrementsScoreByOne() {
    stubLookups();
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.empty());
    when(userVoteRepository.save(any(UserVote.class))).thenAnswer(inv -> inv.getArgument(0));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.UP);

    assertEquals(VoteType.UP, result.getVoteType());
    verify(roomOptionRepository).adjustScore(1L, 1L, 1);
  }

  @Test
  void castVote_newVoteDown_decrementsScoreByOne() {
    stubLookups();
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.empty());
    when(userVoteRepository.save(any(UserVote.class))).thenAnswer(inv -> inv.getArgument(0));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.DOWN);

    assertEquals(VoteType.DOWN, result.getVoteType());
    verify(roomOptionRepository).adjustScore(1L, 1L, -1);
  }

  @Test
  void castVote_sameVote_returnsEarlyWithoutScoreChange() {
    stubLookups();
    UserVote existing = createExistingVote(VoteType.UP);
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.of(existing));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.UP);

    assertEquals(VoteType.UP, result.getVoteType());
    verify(userVoteRepository, never()).save(any());
    verify(roomOptionRepository, never()).adjustScore(any(), any(), eq(0));
  }

  @Test
  void castVote_upToNone_decrementsScoreByOne() {
    stubLookups();
    UserVote existing = createExistingVote(VoteType.UP);
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.of(existing));
    when(userVoteRepository.save(any(UserVote.class))).thenAnswer(inv -> inv.getArgument(0));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.NONE);

    assertEquals(VoteType.NONE, result.getVoteType());
    verify(roomOptionRepository).adjustScore(1L, 1L, -1);
  }

  @Test
  void castVote_upToDown_adjustsScoreByMinusTwo() {
    stubLookups();
    UserVote existing = createExistingVote(VoteType.UP);
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.of(existing));
    when(userVoteRepository.save(any(UserVote.class))).thenAnswer(inv -> inv.getArgument(0));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.DOWN);

    assertEquals(VoteType.DOWN, result.getVoteType());
    verify(roomOptionRepository).adjustScore(1L, 1L, -2);
  }

  @Test
  void castVote_downToUp_adjustsScoreByPlusTwo() {
    stubLookups();
    UserVote existing = createExistingVote(VoteType.DOWN);
    UserVoteId voteId = new UserVoteId(1L, 1L, 1L);
    when(userVoteRepository.findById(voteId)).thenReturn(Optional.of(existing));
    when(userVoteRepository.save(any(UserVote.class))).thenAnswer(inv -> inv.getArgument(0));

    UserVoteResponseDTO result = userVoteService.castVote(roomUuid, userUuid, optionUuid, VoteType.UP);

    assertEquals(VoteType.UP, result.getVoteType());
    verify(roomOptionRepository).adjustScore(1L, 1L, 2);
  }

  @Test
  void castVote_roomUserNotFound_throwsResourceNotFoundException() {
    UUID unknownUserUuid = UUID.randomUUID();
    when(roomUserRepository.findByUuids(roomUuid, unknownUserUuid)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> userVoteService.castVote(roomUuid, unknownUserUuid, optionUuid, VoteType.UP));
  }

  @Test
  void castVote_roomOptionNotFound_throwsResourceNotFoundException() {
    when(roomUserRepository.findByUuids(roomUuid, userUuid)).thenReturn(Optional.of(roomUser));
    UUID unknownOptionUuid = UUID.randomUUID();
    when(roomOptionRepository.findByUuids(roomUuid, unknownOptionUuid)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> userVoteService.castVote(roomUuid, userUuid, unknownOptionUuid, VoteType.UP));
  }

  private UserVote createExistingVote(VoteType voteType) {
    UserVote vote = new UserVote();
    vote.setUserVoteId(new UserVoteId(1L, 1L, 1L));
    vote.setRoomUser(roomUser);
    vote.setRoomOption(roomOption);
    vote.setVoteType(voteType);
    return vote;
  }
}
