package com.example.restaurantVotingApplicationOnSpringBoot.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public abstract class AbstractEnabledDto extends AbstractNamedDto {

    @NotNull
    protected Boolean enabled;

    public AbstractEnabledDto(Integer id, String name, Boolean enabled) {
        super(id, name);
        this.enabled = enabled;
    }
}
