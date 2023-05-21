package com.example.restaurantvoting.web.menu;

import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithRestaurantId;
import com.example.restaurantvoting.web.MatcherFactory;
import lombok.experimental.UtilityClass;

import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.ITALIAN_RESTAURANT_ID;
import static com.example.restaurantvoting.web.restaurant.RestaurantTestData.adminMenuDto1;

@UtilityClass
public class MenuTestData {

    public static final MatcherFactory.Matcher<AdminMenuDtoWithRestaurantId> ADMIN_MENU_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(AdminMenuDtoWithRestaurantId.class);

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static AdminMenuDtoWithRestaurantId getNewTo() {
        return new AdminMenuDtoWithRestaurantId(null, "New menu item", ITALIAN_RESTAURANT_ID, 100L);
    }

    public static Menu getUpdated() {
        return new Menu(adminMenuDto1.getId(), "Updated menu item", 100L,
                null);
    }

    public static AdminMenuDtoWithRestaurantId getUpdatedTo() {
        return new AdminMenuDtoWithRestaurantId(
                null, getUpdated().getName(), ITALIAN_RESTAURANT_ID, getUpdated().getPrice());
    }
}
