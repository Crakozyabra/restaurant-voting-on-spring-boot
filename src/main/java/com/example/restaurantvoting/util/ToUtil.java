package com.example.restaurantvoting.util;

import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.model.Vote;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithRestaurantId;
import com.example.restaurantvoting.to.menu.AdminMenuDto;
import com.example.restaurantvoting.to.menu.UserMenuDto;
import com.example.restaurantvoting.to.restaurant.AdminRestaurantDtoWithAdminMenuDtos;
import com.example.restaurantvoting.to.restaurant.RestaurantDto;
import com.example.restaurantvoting.to.restaurant.UserRestaurantDtoWithUserMenuDtos;
import com.example.restaurantvoting.to.vote.VoteDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ToUtil {

    public static AdminMenuDtoWithRestaurantId menuToAdminMenuDtoWithRestaurantId(Menu menu, Integer restaurantId) {
        return new AdminMenuDtoWithRestaurantId(menu.getId(), menu.getName(), restaurantId, menu.getPrice());
    }

    public static Menu adminMenuDtoWithRestaurantIdToMenu(AdminMenuDtoWithRestaurantId adminMenuDtoWithRestaurantId) {
        return new Menu(adminMenuDtoWithRestaurantId.getId(), adminMenuDtoWithRestaurantId.getName(),
                adminMenuDtoWithRestaurantId.getPrice(), null);
    }

    public static AdminMenuDto menuToAdminMenuDto(Menu menu) {
        return new AdminMenuDto(menu.getId(), menu.getName(), menu.getPrice());
    }

    public static List<AdminMenuDto> menusToAdminMenuDtos(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToAdminMenuDto).collect(Collectors.toList());
    }

    public static VoteDto voteToVoteDto(Vote vote) {
        return new VoteDto(vote.getRestaurant().getId());
    }

    public static AdminRestaurantDtoWithAdminMenuDtos restaurantToAdminRestaurantDtoWithAdminMenuDtos(
            Restaurant restaurant) {
        return new AdminRestaurantDtoWithAdminMenuDtos(
                restaurant.getId(), restaurant.getName(),
                ToUtil.menusToAdminMenuDtos(restaurant.getMenus()));
    }

    public static List<AdminRestaurantDtoWithAdminMenuDtos> restaurantsToAdminRestaurantDtosWithAdminMenuDtos(
            List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToAdminRestaurantDtoWithAdminMenuDtos)
                .collect(Collectors.toList());
    }

    public static Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto) {
        return new Restaurant(
                restaurantDto.getId(), restaurantDto.getName(), null, null);
    }

    public static UserMenuDto menuToUserMenuDto(Menu menu) {
        return new UserMenuDto(menu.getName(), menu.getPrice());
    }

    public static List<UserMenuDto> menusToUserMenuDtos(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToUserMenuDto).collect(Collectors.toList());
    }

    public static UserRestaurantDtoWithUserMenuDtos restaurantToUserRestaurantDtoWithUserMenuDtos(
            Restaurant restaurant) {
        return new UserRestaurantDtoWithUserMenuDtos(restaurant.getId(), restaurant.getName(),
                menusToUserMenuDtos(restaurant.getMenus()));
    }

    public static List<UserRestaurantDtoWithUserMenuDtos> restaurantsToUserRestaurantDtosWithUserMenuDtos(
            List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToUserRestaurantDtoWithUserMenuDtos)
                .collect(Collectors.toList());
    }

    public static RestaurantDto restaurantToRestaurantDto(Restaurant restaurant) {
        return new RestaurantDto(restaurant.getId(), restaurant.getName());
    }

    public static List<RestaurantDto> restaurantsToRestaurantDtos(List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToRestaurantDto).collect(Collectors.toList());
    }
}
