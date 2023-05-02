package com.example.restaurantvoting.web.vote;

import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.repository.VoteRepository;
import com.example.restaurantvoting.service.VoteService;
import com.example.restaurantvoting.to.vote.VoteDto;
import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static com.example.restaurantvoting.UserTestData.*;
import static com.example.restaurantvoting.web.UserVoteController.REST_URL;
import static com.example.restaurantvoting.web.UserVoteController.SUB_REST_URL_VOTES_PATH;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static com.example.restaurantvoting.web.vote.VoteTestData.getSavedTo;
import static com.example.restaurantvoting.web.vote.VoteTestData.getVoteResult;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserVoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(USER_EMAIL)
    public void create() throws Exception {
        VoteDto newVoteDto = VoteTestData.getNewTo();
        perform(MockMvcRequestBuilders.post(REST_URL + SUB_REST_URL_VOTES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HEADER_LOCATION))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Optional<Vote> saved = voteRepository.findVoteByUser_IdAndVotingDateIs(USER_ID, LocalDate.now());
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(saved.get().getRestaurant().getId(), newVoteDto.getRestaurantId());
    }

    @Test
    @WithUserDetails(GUEST_EMAIL)
    public void createNoAuth() throws Exception {
        VoteDto newVoteDto = VoteTestData.getNewTo();
        perform(MockMvcRequestBuilders.post(REST_URL + SUB_REST_URL_VOTES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SUB_REST_URL_VOTES_PATH + "/by-date")
                .queryParam("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(getSavedTo()))));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getAllResultsToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + SUB_REST_URL_VOTES_PATH)
                .queryParam("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(getVoteResult()))));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void updateBeforeTimeVotingLimit() throws Exception {
        voteService.setTimeVotingLimit(LocalTime.MAX);
        VoteDto forUpdate = new VoteDto(RUSSIAN_RESTAURANT_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + SUB_REST_URL_VOTES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(forUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> updated = voteRepository.findVoteByUser_IdAndVotingDateIs(ADMIN_ID, LocalDate.now());
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(updated.get().getRestaurant().getId(), RUSSIAN_RESTAURANT_ID);
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void updateAfterTimeVotingLimit() throws Exception {
        voteService.setTimeVotingLimit(LocalTime.MIN);
        VoteDto forUpdate = new VoteDto(RUSSIAN_RESTAURANT_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + SUB_REST_URL_VOTES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(forUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> updated = voteRepository.findVoteByUser_IdAndVotingDateIs(ADMIN_ID, LocalDate.now());
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(updated.get().getRestaurant().getId(), ITALIAN_RESTAURANT_ID);
    }
}
