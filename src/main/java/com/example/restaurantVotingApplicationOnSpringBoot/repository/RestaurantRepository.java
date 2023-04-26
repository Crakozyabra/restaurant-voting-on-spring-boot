package com.example.restaurantVotingApplicationOnSpringBoot.repository;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id=:id")
    Restaurant get(@Param("id") int id);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.enabled=true ORDER BY r.name")
    List<Restaurant> getAllWithVisibleMenu();

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menus m ORDER BY r.name")
    List<Restaurant> getAllWithMenu();
}
