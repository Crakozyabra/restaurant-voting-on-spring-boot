package com.example.restaurantvoting.to.menu;

import com.example.restaurantvoting.to.AbstractNamedDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminMenuDto extends AbstractNamedDto {

    @Min(0)
    @NotNull
    protected Long price;

    public AdminMenuDto(Integer id, String name, Long price) {
        super(id, name);
        this.price = price;
    }
}
