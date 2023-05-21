package com.example.restaurantvoting.web.restaurant;

import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.restaurantvoting.UserTestData.USER_EMAIL;
import static com.example.restaurantvoting.web.UserRestaurantController.REST_URL;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestaurantControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(RESTAURANT_NAME)))
                .andExpect(content().string(containsString(JsonUtil.writeValue(userMenuDto4))))
                .andExpect(content().string(containsString(JsonUtil.writeValue(userMenuDto5))));
    }
}
