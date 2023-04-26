package com.example.restaurantVotingApplicationOnSpringBoot.to.menu;


import com.example.restaurantVotingApplicationOnSpringBoot.to.AbstractEnabledDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class AdminMenuDto extends AbstractEnabledDto {

    @Min(1)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer restaurantId;

    @Min(0)
    @NotNull
    private Double price;

    public AdminMenuDto(Integer id, String name, Boolean enabled, Integer restaurantId, Double price) {
        super(id, name, enabled);
        this.restaurantId = restaurantId;
        this.price = price;
    }
}
