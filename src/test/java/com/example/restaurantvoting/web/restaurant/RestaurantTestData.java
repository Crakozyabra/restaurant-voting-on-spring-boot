package com.example.restaurantvoting.web.restaurant;

import com.example.restaurantvoting.to.menu.AdminMenuDtoWithoutRestaurantId;
import com.example.restaurantvoting.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantvoting.web.MatcherFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantTestData {
    public static final MatcherFactory.Matcher<RestaurantWithoutMenuDto> RESTAURANT_WITHOUT_MENU_DTO_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(RestaurantWithoutMenuDto.class);

    public static final Integer ITALIAN_RESTAURANT_ID = 1;
    public static final Integer RUSSIAN_RESTAURANT_ID = 3;
    public static final String HEADER_LOCATION = "Location";
    public static final String RESTAURANT_NAME = "Italian restaurant";

    public static RestaurantWithoutMenuDto italianRestaurantDto = new RestaurantWithoutMenuDto(
            ITALIAN_RESTAURANT_ID, "Italian restaurant");

    public static AdminMenuDtoWithoutRestaurantId adminMenuDto1 =
            new AdminMenuDtoWithoutRestaurantId(1, "pizza", false, 106L);
    public static AdminMenuDtoWithoutRestaurantId adminMenuDto2 =
            new AdminMenuDtoWithoutRestaurantId(2, "risotto", true, 201L);
    public static AdminMenuDtoWithoutRestaurantId adminMenuDto3 =
            new AdminMenuDtoWithoutRestaurantId(3, "lasagne", true, 302L);
    public static AdminMenuDtoWithoutRestaurantId adminMenuDto4 =
            new AdminMenuDtoWithoutRestaurantId(4, "ravioli", true, 403L);
    public static AdminMenuDtoWithoutRestaurantId adminMenuDto5 =
            new AdminMenuDtoWithoutRestaurantId(5, "bryschetta", true, 503L);

    public static RestaurantWithoutMenuDto getNewTo() {
        return new RestaurantWithoutMenuDto(null, "New restaurant");
    }

    public static RestaurantWithoutMenuDto getUpdateTo() {
        return new RestaurantWithoutMenuDto(null, "Update restaurant");
    }


}
