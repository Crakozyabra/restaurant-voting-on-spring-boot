package com.example.restaurantvoting.to.restaurant;


import com.example.restaurantvoting.to.AbstractNamedDto;
import com.example.restaurantvoting.to.menu.UserMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class UserRestaurantDto extends AbstractNamedDto {

    @NotNull
    private List<UserMenuDto> menus;

    public UserRestaurantDto(Integer id, String name, List<UserMenuDto> menus) {
        super(id, name);
        this.menus = menus;
    }
}
