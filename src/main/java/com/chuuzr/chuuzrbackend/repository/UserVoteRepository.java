package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.UserVote;
import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;

public interface UserVoteRepository extends JpaRepository<UserVote, UserVoteId> {

}
