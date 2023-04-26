package com.example.restaurantVotingApplicationOnSpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@NamedEntityGraph(name = "userWithJoinFetchRoles", attributeNodes = {
        @NamedAttributeNode("roles")
})
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 128)
    private String email;

    @NotNull
    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private Boolean enabled = true;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Vote> votes;

    public User(Integer id, String name, String email, Boolean enabled, String password, Set<Role> roles, List<Vote> votes) {
        super(id, name);
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
        this.votes = votes;
    }
}
