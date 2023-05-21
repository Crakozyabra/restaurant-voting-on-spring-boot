package com.example.restaurantvoting.to.restaurant;


import com.example.restaurantvoting.to.AbstractNamedDto;
import com.example.restaurantvoting.to.menu.AdminMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class AdminRestaurantDtoWithAdminMenuDtos extends AbstractNamedDto {

    @NotNull
    private List<AdminMenuDto> menus;

    public AdminRestaurantDtoWithAdminMenuDtos(Integer id, String name, List<AdminMenuDto> menus) {
        super(id, name);
        this.menus = menus;
    }
}
