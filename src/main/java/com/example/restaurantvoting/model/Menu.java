package com.example.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Cache;

@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter @Setter
@NoArgsConstructor
public class Menu extends AbstractNamedEntity {

    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Long price;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    @NotNull
    private Boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu(Integer id, String name, Long price, Boolean enabled, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.enabled = enabled;
        this.restaurant = restaurant;
    }
}
