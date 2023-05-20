package com.example.restaurantvoting.web;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.restaurant.UserRestaurantDto;
import com.example.restaurantvoting.util.ToUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.example.restaurantvoting.web.UserVoteController.RESTAURANTS_WITH_MENU_CACHE_NAME;

@AllArgsConstructor
@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/api/user/restaurants";

    private RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable(RESTAURANTS_WITH_MENU_CACHE_NAME)
    public List<UserRestaurantDto> getWithMenu() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithMenu(LocalDate.now());
        return ToUtil.restaurantsToUserRestaurantTos(restaurants);
    }
}
