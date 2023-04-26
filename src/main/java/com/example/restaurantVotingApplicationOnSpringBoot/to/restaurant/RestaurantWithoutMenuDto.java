package com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractNamedDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestaurantWithoutMenuDto extends AbstractNamedDto {

    public RestaurantWithoutMenuDto(Integer id, String name) {
        super(id, name);
    }
}
