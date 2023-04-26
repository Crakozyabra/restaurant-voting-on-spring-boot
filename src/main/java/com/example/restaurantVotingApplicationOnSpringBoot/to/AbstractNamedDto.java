package com.example.restaurantVotingApplicationOnSpringBoot.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public abstract class AbstractNamedDto extends AbstractBaseDto {

    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public AbstractNamedDto(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
