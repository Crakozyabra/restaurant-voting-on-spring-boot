package com.example.restaurantvoting.to.restaurant;


import com.example.restaurantvoting.to.AbstractNamedDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantDto extends AbstractNamedDto {

    public RestaurantDto(Integer id, String name) {
        super(id, name);
    }
}
