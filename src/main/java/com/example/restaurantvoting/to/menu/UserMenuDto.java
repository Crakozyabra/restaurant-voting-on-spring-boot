package com.example.restaurantvoting.to.menu;


import com.example.restaurantvoting.util.validation.NoHtml;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserMenuDto {

    @NoHtml
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @Min(0)
    @NotNull
    private Long price;
}
