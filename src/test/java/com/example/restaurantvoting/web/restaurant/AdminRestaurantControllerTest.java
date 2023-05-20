package com.example.restaurantvoting.web.restaurant;

import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.Optional;

import static com.example.restaurantvoting.UserTestData.ADMIN_EMAIL;
import static com.example.restaurantvoting.web.AdminRestaurantController.REST_URL;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void create() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewTo())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HEADER_LOCATION))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(getNewTo().getName())));
        RestaurantWithoutMenuDto response = RESTAURANT_WITHOUT_MENU_DTO_MATCHER.readFromJson(resultActions);
        Restaurant saved = restaurantRepository.findById(response.getId()).orElse(null);
        Assertions.assertTrue(Objects.nonNull(saved));
        Assertions.assertEquals(response.getId(), saved.getId());
        Assertions.assertEquals(response.getName(), saved.getName());
    }
    @Order(2)
    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + ITALIAN_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(italianRestaurantDto))));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + ITALIAN_RESTAURANT_ID + "/with-menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(RESTAURANT_NAME)))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto1))))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto2))))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto3))))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto4))))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto5))));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + ITALIAN_RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RestaurantTestData.getUpdateTo())))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Restaurant> updated = restaurantRepository.findById(ITALIAN_RESTAURANT_ID);
        Assertions.assertTrue(updated.isPresent());
        Assertions.assertEquals(getUpdateTo().getName(), updated.get().getName());
    }
    @Order(1)
    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + ITALIAN_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Restaurant> deleted = restaurantRepository.findById(ITALIAN_RESTAURANT_ID);
        Assertions.assertFalse(deleted.isPresent());
    }

    @Test
    public void deleteNoAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + ITALIAN_RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
