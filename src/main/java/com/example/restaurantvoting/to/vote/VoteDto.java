package com.example.restaurantvoting.to.vote;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class VoteDto {

    @NotNull
    @Min(1)
    private Integer restaurantId;

    public VoteDto(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
