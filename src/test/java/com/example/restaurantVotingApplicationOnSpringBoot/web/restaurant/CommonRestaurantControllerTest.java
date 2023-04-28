package com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant;

import com.example.restaurantVotingApplicationOnSpringBoot.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.restaurantVotingApplicationOnSpringBoot.web.CommonRestaurantController.REST_URL;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.RESTAURANT_NAME;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommonRestaurantControllerTest extends AbstractControllerTest {

    @Test
    public void getAllWithVisibleMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/restaurants/with-menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(RESTAURANT_NAME)));
    }
}
