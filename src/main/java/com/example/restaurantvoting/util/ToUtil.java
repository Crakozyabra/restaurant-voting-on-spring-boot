package com.example.restaurantvoting.util;

import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.to.menu.AdminMenuDto;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithoutRestaurantId;
import com.example.restaurantvoting.to.menu.UserMenuDto;
import com.example.restaurantvoting.to.restaurant.AdminRestaurantDto;
import com.example.restaurantvoting.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantvoting.to.restaurant.UserRestaurantDto;
import com.example.restaurantvoting.to.vote.VoteDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ToUtil {

    public static AdminMenuDto menuToAdminMenuDto(Menu menu, Integer restaurantId) {
        return new AdminMenuDto(menu.getId(), menu.getName(), restaurantId, menu.getPrice());
    }

    public static Menu adminMenuDtoToMenu(AdminMenuDto adminMenuDto) {
        return new Menu(adminMenuDto.getId(), adminMenuDto.getName(), adminMenuDto.getPrice(), null);
    }

    public static AdminMenuDtoWithoutRestaurantId menuToAdminMenuDtoWithoutRestaurantId(Menu menu) {
        return new AdminMenuDtoWithoutRestaurantId(menu.getId(), menu.getName(), menu.getPrice());
    }

    public static List<AdminMenuDtoWithoutRestaurantId> menusToAdminMenusDtoWithoutRestaurantId(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToAdminMenuDtoWithoutRestaurantId).collect(Collectors.toList());
    }

    public static VoteDto voteToVoteDto(Vote vote) {
        return  new VoteDto(vote.getRestaurant().getId());
    }

    public static AdminRestaurantDto restaurantToAdminRestaurantDto(Restaurant restaurant) {
        return new AdminRestaurantDto(
                restaurant.getId(), restaurant.getName(),
                ToUtil.menusToAdminMenusDtoWithoutRestaurantId(restaurant.getMenus()));
    }

    public static List<AdminRestaurantDto> restaurantsToRestaurantsTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToAdminRestaurantDto).collect(Collectors.toList());
    }

    public static Restaurant restaurantWithoutMenuDtoToRestaurant(RestaurantWithoutMenuDto restaurantWithoutMenuDto) {
        return new Restaurant(
                restaurantWithoutMenuDto.getId(), restaurantWithoutMenuDto.getName(), null, null);
    }

    public static UserMenuDto menuToUserMenuDto(Menu menu) {
        return new UserMenuDto(menu.getName(), menu.getPrice());
    }

    public static List<UserMenuDto> menusToUserMenusDto(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToUserMenuDto).collect(Collectors.toList());
    }

    public static UserRestaurantDto restaurantToUserRestaurantTo(Restaurant restaurant) {
        return new UserRestaurantDto(restaurant.getId(), restaurant.getName(),
                menusToUserMenusDto(restaurant.getMenus()));
    }

    public static List<UserRestaurantDto> restaurantsToUserRestaurantTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToUserRestaurantTo).collect(Collectors.toList());
    }

    public static RestaurantWithoutMenuDto restaurantToRestaurantWithoutMenuDto(Restaurant restaurant) {
        return new RestaurantWithoutMenuDto(restaurant.getId(), restaurant.getName());
    }

    public static List<RestaurantWithoutMenuDto> restaurantsToRestaurantWithoutMenuTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToRestaurantWithoutMenuDto).collect(Collectors.toList());
    }
}
