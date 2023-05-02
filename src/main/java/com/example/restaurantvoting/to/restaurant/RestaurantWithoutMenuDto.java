package com.example.restaurantvoting.to.restaurant;


import com.example.restaurantvoting.to.AbstractNamedDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantWithoutMenuDto extends AbstractNamedDto {

    public RestaurantWithoutMenuDto(Integer id, String name) {
        super(id, name);
    }
}
