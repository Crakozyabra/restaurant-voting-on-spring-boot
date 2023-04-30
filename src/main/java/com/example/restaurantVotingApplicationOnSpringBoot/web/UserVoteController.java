package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.service.VoteService;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.UserRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user";

    public static final String SUB_REST_URL_VOTES_PATH = "/votes";

    public static final String RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME = "getRestaurantsWithVisibleMenu";

    private VoteService voteService;

    private RestaurantRepository restaurantRepository;

    @PostMapping(SUB_REST_URL_VOTES_PATH)
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto,
                                          @AuthenticationPrincipal AuthUser authUser) {
        VoteDto created = voteService.create(voteDto, authUser.id());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/by-date").query("date=" + LocalDate.now()).build().toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(created);
    }

    @GetMapping(SUB_REST_URL_VOTES_PATH + "/by-date")
    public VoteDto getByDate(@Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
                             @RequestParam LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        return voteService.getByDate(authUser.id(), date);
    }

    @GetMapping(SUB_REST_URL_VOTES_PATH)
    public List<VotesDto> getAllResultsToday(
            @Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
            @RequestParam LocalDate date) {
        return voteService.getAllResultsToday(date);
    }

    @PutMapping(SUB_REST_URL_VOTES_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteDto voteDto, @AuthenticationPrincipal AuthUser authUser) {
        voteService.update(voteDto, authUser.id());
    }

    @GetMapping("/restaurants")
    @Cacheable(RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME)
    public List<UserRestaurantDto> getRestaurantsWithVisibleMenu() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithVisibleMenu();
        return ToUtil.restaurantsToUserRestaurantTos(restaurants);
    }
}

