package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.service.VoteService;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    public static final String REST_URL = "/api/user/votes";

    private VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto,
                                          @AuthenticationPrincipal AuthUser authUser) {
        VoteDto created = voteService.create(voteDto, authUser.id());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/by-date").query("date=" + LocalDate.now()).build().toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(created);
    }

    @GetMapping("/by-date")
    public VoteDto getByDate(@Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
                                 @RequestParam LocalDate date,  @AuthenticationPrincipal AuthUser authUser) {
        return voteService.getByDate(authUser.id(), date);
    }

    @GetMapping
    public List<VotesDto> getAllResultsToday(
            @Parameter(description = "Format example: 2024-02-29. Enter today date", required = true) @RequestParam LocalDate date) {
        return voteService.getAllResultsToday(date);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteDto voteDto, @AuthenticationPrincipal AuthUser authUser) {
        voteService.update(voteDto, authUser.id());
    }
}

