package com.example.restaurantvoting.web.menu;

import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.repository.MenuRepository;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithRestaurantId;
import com.example.restaurantvoting.util.JsonUtil;
import com.example.restaurantvoting.util.ToUtil;
import com.example.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.example.restaurantvoting.UserTestData.ADMIN_EMAIL;
import static com.example.restaurantvoting.UserTestData.USER_EMAIL;
import static com.example.restaurantvoting.web.AdminMenuController.REST_URL;
import static com.example.restaurantvoting.web.menu.MenuTestData.ADMIN_MENU_DTO_MATCHER;
import static com.example.restaurantvoting.web.menu.MenuTestData.MENU_MATCHER;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class AdminMenuControllerTest extends AbstractControllerTest {

    @Autowired
    MenuRepository menuRepository;

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void create() throws Exception {
        AdminMenuDtoWithRestaurantId newAdminMenuDtoWithRestaurantId = MenuTestData.getNewTo();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newAdminMenuDtoWithRestaurantId)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HEADER_LOCATION))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        AdminMenuDtoWithRestaurantId response = ADMIN_MENU_DTO_MATCHER.readFromJson(resultActions);
        Menu saved = menuRepository.findById(response.getId()).orElse(null);
        Assertions.assertTrue(Objects.nonNull(saved));
        response.setId(saved.getId());
        ADMIN_MENU_DTO_MATCHER.assertMatch(ToUtil.menuToAdminMenuDtoWithRestaurantId(saved, newAdminMenuDtoWithRestaurantId.getRestaurantId()), response);
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + adminMenuDto1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(JsonUtil.writeValue(adminMenuDto1))));
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void update() throws Exception {
        AdminMenuDtoWithRestaurantId forUpdateTo = MenuTestData.getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + adminMenuDto1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(forUpdateTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Menu updated = menuRepository.findById(adminMenuDto1.getId()).orElse(null);
        Menu forUpdate = ToUtil.adminMenuDtoWithRestaurantIdToMenu(forUpdateTo);
        forUpdate.setId(adminMenuDto1.getId());
        forUpdate.setCreatedDate(LocalDate.now().minusDays(1));
        MENU_MATCHER.assertMatch(updated, forUpdate);
    }

    @Test
    @WithUserDetails(ADMIN_EMAIL)
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + adminMenuDto1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        Optional<Menu> deleted = menuRepository.findById(ITALIAN_RESTAURANT_ID);
        Assertions.assertFalse(deleted.isPresent());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void deleteNoAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + adminMenuDto1.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
