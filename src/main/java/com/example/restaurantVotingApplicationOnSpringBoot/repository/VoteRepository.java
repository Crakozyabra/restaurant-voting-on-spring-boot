package com.example.restaurantVotingApplicationOnSpringBoot.repository;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @EntityGraph(value = "voteWithJoinFetchRestaurant")
    List<Vote> findVoteByVotingDateIs(LocalDate votingDate);

    Optional<Vote> findVoteByUser_IdAndVotingDateIs(Integer userId, LocalDate votingDate);
}
