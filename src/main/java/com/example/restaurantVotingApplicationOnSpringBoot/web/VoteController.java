package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.service.VoteService;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.UserRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = "/user";

    private VoteService voteService;

    private RestaurantRepository restaurantRepository;

    @PostMapping("/votes")
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto) {
        ValidationUtil.checkNew(voteDto);
        VoteDto created = voteService.create(voteDto);
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(created);
    }

    @GetMapping("/votes")
    public List<VotesDto> getAllResultsToday(@RequestParam LocalDate date) {
        return voteService.getAllResultsToday(date);
    }

    @PutMapping("votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteDto voteDto, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(voteDto, id);
        voteService.update(voteDto);
    }

    @GetMapping("/restaurants")
    public List<UserRestaurantDto> getAllWithVisibleMenu() {
        List<Restaurant> restaurants = restaurantRepository.getAllWithVisibleMenu();
        return restaurants.stream().map(ToUtil::restaurantToUserRestaurantTo).collect(Collectors.toList());
    }
}

