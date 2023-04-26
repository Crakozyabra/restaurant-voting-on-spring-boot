package com.example.restaurantVotingApplicationOnSpringBoot.repository;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
