package com.example.restaurantvoting.to;


import com.example.restaurantvoting.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public abstract class AbstractBaseDto implements HasId {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    public AbstractBaseDto(Integer id) {
        this.id = id;
    }
}
