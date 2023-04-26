package com.example.restaurantVotingApplicationOnSpringBoot.service;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.model.User;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Vote;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.UserRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.VoteRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final static LocalTime TIME_LIMIT_FOR_VOTING = LocalTime.of(11, 0, 0);

    private VoteRepository voteRepository;

    private RestaurantRepository restaurantRepository;

    private UserRepository userRepository;

    @Transactional
    public VoteDto create(VoteDto voteDto) {
        Restaurant restaurant = restaurantRepository.getReferenceById(voteDto.getRestaurantId());
        User user = userRepository.getReferenceById(voteDto.getUserId());
        Vote vote = voteRepository.save(new Vote(null, restaurant, user, null, null));
        voteDto.setId(vote.getId());
        return voteDto;
    }

    public List<VotesDto> getAllResultsToday(LocalDate votingDay) {
        List<Vote> votes = voteRepository.findVoteByVotingDateIs(votingDay);
        return ToUtil.votesToVotesDto(votes);
    }

    @Transactional
    public void update(VoteDto voteDto) {
        Vote vote = voteRepository.findVoteByUser_IdAndVotingDateIs(voteDto.getUserId(), LocalDate.now());
        Restaurant restaurant = restaurantRepository.getReferenceById(voteDto.getRestaurantId());
        if (vote.getVotingTime().isBefore(TIME_LIMIT_FOR_VOTING)) {
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        }
    }
}

