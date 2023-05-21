package com.example.restaurantvoting.web.restaurant;

import com.example.restaurantvoting.to.menu.AdminMenuDto;
import com.example.restaurantvoting.to.menu.UserMenuDto;
import com.example.restaurantvoting.to.restaurant.RestaurantDto;
import com.example.restaurantvoting.web.MatcherFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantDto> RESTAURANT_WITHOUT_MENU_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantDto.class);

    public static final Integer ITALIAN_RESTAURANT_ID = 1;
    public static final Integer RUSSIAN_RESTAURANT_ID = 3;
    public static final String HEADER_LOCATION = "Location";
    public static final String RESTAURANT_NAME = "Italian restaurant";

    public static RestaurantDto italianRestaurantDto = new RestaurantDto(
            ITALIAN_RESTAURANT_ID, "Italian restaurant");



    public static UserMenuDto userMenuDto4 = new UserMenuDto("ravioli", 403L);
    public static UserMenuDto userMenuDto5 = new UserMenuDto("bryschetta", 503L);

    public static AdminMenuDto adminMenuDto1 =
            new AdminMenuDto(1, "pizza", 106L);
    public static AdminMenuDto adminMenuDto2 =
            new AdminMenuDto(2, "risotto", 201L);
    public static AdminMenuDto adminMenuDto3 =
            new AdminMenuDto(3, "lasagne", 302L);

    public static RestaurantDto getNewTo() {
        return new RestaurantDto(null, "New restaurant");
    }

    public static RestaurantDto getUpdateTo() {
        return new RestaurantDto(null, "Update restaurant");
    }
}
