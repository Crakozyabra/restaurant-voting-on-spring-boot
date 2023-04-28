package com.example.restaurantVotingApplicationOnSpringBoot.web.vote;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Vote;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.VoteRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.service.VoteService;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.JsonUtil;
import com.example.restaurantVotingApplicationOnSpringBoot.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static com.example.restaurantVotingApplicationOnSpringBoot.UserTestData.*;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.UserVoteController.REST_URL;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.*;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.vote.VoteTestData.*;
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
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HEADER_LOCATION))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        VoteDto response = VOTE_DTO_MATCHER.readFromJson(resultActions);
        Optional<Vote> saved = voteRepository.findById(response.getId());
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(saved.get().getRestaurant().getId(), newVoteDto.getRestaurantId());
    }

    @Test
    @WithUserDetails(GUEST_EMAIL)
    public void createNoAuth() throws Exception {
        VoteDto newVoteDto = VoteTestData.getNewTo();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVoteDto)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date").queryParam("date",
                LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(getSavedTo()))));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getAllResultsToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL).queryParam("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(getVoteResult()))));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void updateBeforeTimeVotingLimit() throws Exception {
        voteService.setTimeVotingLimit(LocalTime.MAX);
        Integer savedVoteId = getSavedTo().getId();
        VoteDto forUpdate = new VoteDto(savedVoteId, RUSSIAN_RESTAURANT_ID);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(forUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> updated = voteRepository.findById(savedVoteId);
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(updated.get().getId(), savedVoteId);
        Assertions.assertEquals(updated.get().getRestaurant().getId(), RUSSIAN_RESTAURANT_ID);
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void updateAfterTimeVotingLimit() throws Exception {
        voteService.setTimeVotingLimit(LocalTime.MIN);
        Integer savedVoteId = getSavedTo().getId();
        VoteDto forUpdate = new VoteDto(savedVoteId, RUSSIAN_RESTAURANT_ID);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(forUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Vote> updated = voteRepository.findById(savedVoteId);
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(updated.get().getId(), savedVoteId);
        Assertions.assertEquals(updated.get().getRestaurant().getId(), ITALIAN_RESTAURANT_ID);
    }


}
