package com.example.restaurantVotingApplicationOnSpringBoot.web.menu;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Menu;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.web.MatcherFactory;
import lombok.experimental.UtilityClass;

import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.ITALIAN_RESTAURANT_ID;
import static com.example.restaurantVotingApplicationOnSpringBoot.web.restaurant.RestaurantTestData.adminMenuDto1;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<AdminMenuDto> ADMIN_MENU_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(AdminMenuDto.class);

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static AdminMenuDto getNewTo() {
        return new AdminMenuDto(null, "New menu item", true, ITALIAN_RESTAURANT_ID, 100L);
    }

    public static Menu getUpdated() {
        return new Menu(adminMenuDto1.getId(), "Updated menu item", 100L, true, null);
    }

    public static AdminMenuDto getUpdatedTo() {
        return new AdminMenuDto(
                null, getUpdated().getName(), getUpdated().getEnabled(), ITALIAN_RESTAURANT_ID, getUpdated().getPrice());
    }
}
