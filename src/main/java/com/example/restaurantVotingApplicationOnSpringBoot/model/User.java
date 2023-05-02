package com.example.restaurantVotingApplicationOnSpringBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", indexes = @Index(name = "email_idx", columnList = "email", unique = true))
@Getter @Setter
@NamedEntityGraph(name = "withRoles", attributeNodes = @NamedAttributeNode("roles"))
@NoArgsConstructor
public class User extends AbstractNamedEntity {

    @Column(name = "email", nullable = false)
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
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
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

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
}
