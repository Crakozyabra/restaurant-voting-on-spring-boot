package com.example.restaurantVotingApplicationOnSpringBoot.service;

import com.example.restaurantVotingApplicationOnSpringBoot.error.NotFoundException;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.model.User;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Vote;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.UserRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.VoteRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteService {

    @Setter
    private LocalTime timeVotingLimit;

    private VoteRepository voteRepository;

    private RestaurantRepository restaurantRepository;

    private UserRepository userRepository;

    public VoteService(@Value("${time.voting.limit}") String timeVotingLimit, VoteRepository voteRepository,
                       RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.timeVotingLimit = LocalTime.parse(timeVotingLimit);
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public VoteDto create(VoteDto voteDto, Integer userId) {
        Restaurant restaurant = restaurantRepository.getReferenceById(voteDto.getRestaurantId());
        User user = userRepository.getReferenceById(userId);
        Vote vote = voteRepository.save(new Vote(null, restaurant, user, null, null));
        voteDto.setId(vote.getId());
        return voteDto;
    }

    public VoteDto getByDate(Integer userId, LocalDate date) {
        Vote vote = voteRepository.findVoteByUser_IdAndVotingDateIs(userId, date)
                .orElseThrow(() -> new NotFoundException("Vote not found"));
        return ToUtil.voteToVoteDto(vote);
    }

    public List<VotesDto> getAllResultsToday(LocalDate votingDay) {
        List<Vote> votes = voteRepository.findVoteByVotingDateIs(votingDay);
        return ToUtil.votesToVotesDto(votes);
    }

    @Transactional
    public void update(VoteDto voteDto, Integer userId) {
        Vote vote = voteRepository.findVoteByUser_IdAndVotingDateIs(userId, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Vote not found"));
        Restaurant restaurant = restaurantRepository.getReferenceById(voteDto.getRestaurantId());
        if (vote.getVotingTime().isBefore(timeVotingLimit)) {
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        }
    }
}

