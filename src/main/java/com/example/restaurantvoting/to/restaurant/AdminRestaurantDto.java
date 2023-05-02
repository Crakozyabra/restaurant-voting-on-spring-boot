package com.example.restaurantvoting.to.restaurant;


import com.example.restaurantvoting.to.AbstractNamedDto;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithoutRestaurantId;
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
