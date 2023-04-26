package com.example.restaurantVotingApplicationOnSpringBoot.to.vote;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class VotesDto {

    @NotBlank
    @Size(min = 2, max = 128)
    private String restaurantName;

    @NotNull
    @Min(1)
    private Integer votesCount;

    public VotesDto(String restaurantName, Integer votesCount) {
        this.restaurantName = restaurantName;
        this.votesCount = votesCount;
    }
}
