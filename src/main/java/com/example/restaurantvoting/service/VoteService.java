package com.example.restaurantvoting.service;

import com.example.restaurantvoting.error.DataConflictException;
import com.example.restaurantvoting.error.NotFoundException;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.User;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.repository.UserRepository;
import com.example.restaurantvoting.repository.VoteRepository;
import com.example.restaurantvoting.to.vote.VoteDto;
import com.example.restaurantvoting.util.ToUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional(readOnly = true)
public class VoteService {

    @Setter
    private LocalTime timeVotingLimit;

    private VoteRepository voteRepository;

    private RestaurantRepository restaurantRepository;

    private UserRepository userRepository;

    public VoteService(@Value("${vote.time-limit}") String timeVotingLimit, VoteRepository voteRepository,
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
        voteRepository.save(new Vote(null, restaurant, user, null, null));
        return voteDto;
    }

    public VoteDto getByDate(Integer userId, LocalDate date) {
        Vote vote = voteRepository.findVoteByUser_IdAndVoteDateIs(userId, date)
                .orElseThrow(() -> new NotFoundException("Vote not found"));
        return ToUtil.voteToVoteDto(vote);
    }

    @Transactional
    public void update(VoteDto voteDto, Integer userId) {
        if (LocalTime.now().isAfter(timeVotingLimit)) {
            throw new DataConflictException(String.format("Additional vote after %s not allowed",
                    timeVotingLimit.toString()));
        }
        Vote vote = voteRepository.findVoteByUser_IdAndVoteDateIs(userId, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Vote not found"));
        Restaurant restaurant = restaurantRepository.getReferenceById(voteDto.getRestaurantId());
        if (vote.getVoteTime().isBefore(timeVotingLimit)) {
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        }
    }
}

