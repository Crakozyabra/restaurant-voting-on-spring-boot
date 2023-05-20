package com.example.restaurantvoting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "one_vote_per_date_unique_constraint")})
@Getter @Setter
@NoArgsConstructor
@NamedEntityGraph(name = "voteWithJoinFetchRestaurant", attributeNodes = {
        @NamedAttributeNode("restaurant")
})
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @UpdateTimestamp
    @Column(name = "vote_time", nullable = false)
    private LocalTime voteTime;

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate voteDate, LocalTime voteTime) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.voteDate = voteDate;
        this.voteTime = voteTime;
    }
}
