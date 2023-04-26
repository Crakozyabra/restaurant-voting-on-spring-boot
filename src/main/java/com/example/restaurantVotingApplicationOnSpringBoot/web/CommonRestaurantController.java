package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.UserRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = CommonRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonRestaurantController {

    public static final String REST_URL = "/api/common";

    private RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants")
    public List<UserRestaurantDto> getAllWithVisibleMenu() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithVisibleMenu();
        return restaurants.stream().map(ToUtil::restaurantToUserRestaurantTo).collect(Collectors.toList());
    }
}
