package com.example.restaurantvoting.web;

import com.example.restaurantvoting.service.VoteService;
import com.example.restaurantvoting.to.vote.VoteDto;
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

@AllArgsConstructor
@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user/votes";

    public static final String RESTAURANTS_WITH_MENU_CACHE_NAME = "getRestaurantsWithVisibleMenu";

    private VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto,
                                          @AuthenticationPrincipal AuthUser authUser) {
        VoteDto created = voteService.create(voteDto, authUser.id());
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).query("date=" + LocalDate.now()).build().toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(created);
    }

    @GetMapping
    public VoteDto getByDate(@Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
                             @RequestParam LocalDate date, @AuthenticationPrincipal AuthUser authUser) {
        return voteService.getByDate(authUser.id(), date);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteDto voteDto, @AuthenticationPrincipal AuthUser authUser) {
        voteService.update(voteDto, authUser.id());
    }
}

