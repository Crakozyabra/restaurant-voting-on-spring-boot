package com.example.restaurantVotingApplicationOnSpringBoot.to.menu;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AdminMenuDto extends AdminMenuDtoWithoutRestaurantId {

    @Min(1)
    @NotNull
    private Integer restaurantId;

    public AdminMenuDto(Integer id, String name, Boolean enabled, Integer restaurantId, Double price) {
        super(id, name, enabled, price);
        this.restaurantId = restaurantId;
        this.price = price;
    }
}
