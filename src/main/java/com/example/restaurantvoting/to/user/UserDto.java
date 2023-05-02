package com.example.restaurantvoting.to.user;


import com.example.restaurantvoting.model.Role;
import com.example.restaurantvoting.to.AbstractEnabledDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@NoArgsConstructor
@Getter
public class UserDto extends AbstractEnabledDto {

    @Email
    @NotBlank
    @Size(max = 128)
    private String email;

    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    @NotNull
    private Set<Role> roles;

    public UserDto(Integer id, String name, Boolean enabled, String email, String password, Set<Role> roles) {
        super(id, name, enabled);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
