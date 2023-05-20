package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional(readOnly = true)
    @Query("FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id=:id AND m.createdDate=:createdDate")
    Restaurant getWithMenu(@Param("id") int id, @Param("createdDate") LocalDate createdDate);

    @Transactional(readOnly = true)
    @Query("FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.createdDate=:createdDate")
    List<Restaurant> getAllWithMenu(@Param("createdDate") LocalDate createdDate);
}
