package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.to.vote.VoteDto;
import lombok.experimental.UtilityClass;

import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.ITALIAN_RESTAURANT_ID;

@UtilityClass
public class VoteTestData {

    public static VoteDto getNewTo() {
        return new VoteDto(ITALIAN_RESTAURANT_ID);
    }

    public static VoteDto getSavedTo() {
        return new VoteDto(ITALIAN_RESTAURANT_ID);
    }
}
