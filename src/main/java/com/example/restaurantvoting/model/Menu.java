package com.example.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "dish",
        uniqueConstraints = @UniqueConstraint(
                name = "dish_name_per_date_on_restaurant_unique_constraint",
                columnNames = {"name", "created_date", "restaurant_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Menu extends AbstractNamedEntity {

    @Column(nullable = false)
    @Min(0)
    @NotNull
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu(Integer id, String name, Long price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
}
