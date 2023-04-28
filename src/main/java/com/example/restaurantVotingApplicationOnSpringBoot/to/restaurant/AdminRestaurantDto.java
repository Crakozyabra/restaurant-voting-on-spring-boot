package com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractNamedDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDtoWithoutRestaurantId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class AdminRestaurantDto extends AbstractNamedDto {

    @NotNull
    private List<AdminMenuDtoWithoutRestaurantId> menus;

    public AdminRestaurantDto(Integer id, String name, List<AdminMenuDtoWithoutRestaurantId> menus) {
        super(id, name);
        this.menus = menus;
    }
}
