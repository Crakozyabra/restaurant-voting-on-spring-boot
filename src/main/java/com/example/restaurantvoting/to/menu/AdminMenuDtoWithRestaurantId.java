package com.example.restaurantvoting.to.menu;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AdminMenuDtoWithRestaurantId extends AdminMenuDto {

    @Min(1)
    @NotNull
    private Integer restaurantId;

    public AdminMenuDtoWithRestaurantId(Integer id, String name, Integer restaurantId, Long price) {
        super(id, name, price);
        this.restaurantId = restaurantId;
        this.price = price;
    }
}
