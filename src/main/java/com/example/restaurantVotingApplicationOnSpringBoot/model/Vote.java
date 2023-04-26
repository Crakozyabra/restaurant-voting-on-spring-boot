package com.example.restaurantVotingApplicationOnSpringBoot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
        {@UniqueConstraint(columnNames = {"user_id", "voting_date"}, name = "user_date_unique_constraint")})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "voteWithJoinFetchRestaurant", attributeNodes = {
        @NamedAttributeNode("restaurant")
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "voting_date")
    private LocalDate votingDate;

    @UpdateTimestamp
    @Column(name = "voting_time")
    private LocalTime votingTime;
}
