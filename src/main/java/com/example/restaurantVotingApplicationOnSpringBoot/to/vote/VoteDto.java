package com.example.restaurantVotingApplicationOnSpringBoot.to.vote;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractBaseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class VoteDto extends AbstractBaseDto {

    @NotNull
    @Min(1)
    private Integer restaurantId;

    public VoteDto(Integer id, Integer restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }
}
