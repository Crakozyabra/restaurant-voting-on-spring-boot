package com.example.restaurantvoting.web.menu;

import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.to.menu.AdminMenuDto;
import com.example.restaurantvoting.web.MatcherFactory;
import lombok.experimental.UtilityClass;

import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.ITALIAN_RESTAURANT_ID;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.adminMenuDto1;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<AdminMenuDto> ADMIN_MENU_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(AdminMenuDto.class);

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static AdminMenuDto getNewTo() {
        return new AdminMenuDto(null, "New menu item", ITALIAN_RESTAURANT_ID, 100L);
    }

    public static Menu getUpdated() {
        return new Menu(adminMenuDto1.getId(), "Updated menu item", 100L,
                null);
    }

    public static AdminMenuDto getUpdatedTo() {
        return new AdminMenuDto(
                null, getUpdated().getName(), ITALIAN_RESTAURANT_ID, getUpdated().getPrice());
    }
}
