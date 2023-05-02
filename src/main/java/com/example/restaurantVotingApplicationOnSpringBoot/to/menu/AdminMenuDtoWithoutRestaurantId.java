package com.example.restaurantVotingApplicationOnSpringBoot.to.menu;

import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractEnabledDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuDtoWithoutRestaurantId extends AbstractEnabledDto {

    @Min(0)
    @NotNull
    protected Long price;

    public AdminMenuDtoWithoutRestaurantId(Integer id, String name, Boolean enabled, Long price) {
        super(id, name, enabled);
        this.price = price;
    }
}
