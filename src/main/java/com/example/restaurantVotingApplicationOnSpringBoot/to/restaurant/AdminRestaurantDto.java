package com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractNamedDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@NoArgsConstructor
public class AdminRestaurantDto extends AbstractNamedDto {

    @NotNull
    private List<AdminMenuDto> menus;

    public AdminRestaurantDto(Integer id, String name, List<AdminMenuDto> menus) {
        super(id, name);
        this.menus = menus;
    }
}
