package com.example.restaurantVotingApplicationOnSpringBoot.to.menu;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractNamedDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserMenuDto extends AbstractNamedDto {

    @Min(0)
    @NotNull
    private Double price;

    public UserMenuDto(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }
}
