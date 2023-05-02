package com.example.restaurantvoting.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter @Setter
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity{

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant(Integer id, String name, List<Menu> menus, Set<Vote> votes) {
        super(id, name);
        this.menus = menus;
        this.votes = votes;
    }
}