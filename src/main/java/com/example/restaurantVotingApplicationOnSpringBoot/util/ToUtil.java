package com.example.restaurantVotingApplicationOnSpringBoot.util;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Menu;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.model.User;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Vote;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.UserMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.AdminRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.UserRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.user.UserDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VoteDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.vote.VotesDto;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ToUtil {

    public static AdminMenuDto menuToAdminMenuDto(Menu menu) {
        return new AdminMenuDto(menu.getId(), menu.getName(), menu.getEnabled(), menu.getRestaurant().getId(),
                menu.getPrice());
    }

    public static Menu adminMenuDtoToMenu(AdminMenuDto adminMenuDto) {
        return new Menu(adminMenuDto.getId(), adminMenuDto.getName(), adminMenuDto.getPrice(),
                adminMenuDto.getEnabled(), null);
    }

    public static List<AdminMenuDto> menusToAdminMenusDto(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToAdminMenuDto).collect(Collectors.toList());
    }

    public static AdminRestaurantDto restaurantToAdminRestaurantDto(Restaurant restaurant) {
        return new AdminRestaurantDto(
                restaurant.getId(), restaurant.getName(), ToUtil.menusToAdminMenusDto(restaurant.getMenus()));
    }

    public static Restaurant restaurantWithoutMenuDtoToRestaurant(RestaurantWithoutMenuDto restaurantWithoutMenuDto) {
        return new Restaurant(
                restaurantWithoutMenuDto.getId(), restaurantWithoutMenuDto.getName(), null, null);
    }

    /*public static RestaurantWithoutMenuDto restaurantToRestaurantWithoutMenuDto(Restaurant restaurant) {
        return new RestaurantWithoutMenuDto(restaurant.getId(), restaurant.getName());
    }

    public static Restaurant restaurantDtoToRestaurant(AdminRestaurantDto adminRestaurantDto) {
        return new Restaurant(adminRestaurantDto.getId(), adminRestaurantDto.getName(), null, null);
    }*/

    public static List<VotesDto> votesToVotesDto(List<Vote> votes) {
        Map<String, Integer> voting = new LinkedHashMap<>();
        for (Vote vote : votes) {
            voting.merge(vote.getRestaurant().getName(), 1, (oldValue, newValue) -> oldValue + 1);
        }
        List<VotesDto> votesDto = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : voting.entrySet()) {
            votesDto.add(new VotesDto(entry.getKey(), entry.getValue()));
        }
        return votesDto;
    }

    /*public static VoteDto voteToVoteDto(Integer voteId, Integer userId, Integer restaurantId) {
        return new VoteDto();
    }

    public static UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEnabled(), user.getEmail(), user.getPassword(),
                user.getRoles());
    }

    public static User userDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getEnabled(),
                userDto.getPassword(), userDto.getRoles(), null);
    }*/

    public static UserMenuDto menuToUserMenuDto(Menu menu) {
        return new UserMenuDto(menu.getId(), menu.getName(), menu.getPrice());
    }

    public static List<UserMenuDto> menusToUserMenusDto(List<Menu> menus) {
        return menus.stream().map(ToUtil::menuToUserMenuDto).collect(Collectors.toList());
    }

    public static UserRestaurantDto restaurantToUserRestaurantTo(Restaurant restaurant) {
        return new UserRestaurantDto(restaurant.getId(), restaurant.getName(),
                menusToUserMenusDto(restaurant.getMenus()));
    }

    public static List<AdminRestaurantDto> restaurantsToRestaurantsTos(List<Restaurant> restaurants) {
        return restaurants.stream().map(ToUtil::restaurantToAdminRestaurantDto).collect(Collectors.toList());
    }
}
