package com.example.restaurantvoting.to.menu;

import com.example.restaurantvoting.to.AbstractNamedDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuDtoWithoutRestaurantId extends AbstractNamedDto {

    @Min(0)
    @NotNull
    protected Long price;

    public AdminMenuDtoWithoutRestaurantId(Integer id, String name, Long price) {
        super(id, name);
        this.price = price;
    }
}
