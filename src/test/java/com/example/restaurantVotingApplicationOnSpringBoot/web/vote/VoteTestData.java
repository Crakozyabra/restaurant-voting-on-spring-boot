package com.example.restaurantVotingApplicationOnSpringBoot.web.vote;

import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import lombok.experimental.UtilityClass;

import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.ITALIAN_RESTAURANT_ID;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.italianRestaurantDto;

@UtilityClass
public class VoteTestData {

    public static VoteDto getNewTo() {
        return new VoteDto(ITALIAN_RESTAURANT_ID);
    }

    public static VoteDto getSavedTo() {
        return new VoteDto(ITALIAN_RESTAURANT_ID);
    }

    public static VotesDto getVoteResult() {
        return new VotesDto(italianRestaurantDto.getName(), 1);
    }
}
