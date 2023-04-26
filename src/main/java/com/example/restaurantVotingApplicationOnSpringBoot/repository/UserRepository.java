package com.example.restaurantVotingApplicationOnSpringBoot.repository;

import com.example.restaurantVotingApplicationOnSpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User u LEFT JOIN FETCH u.roles")
    List<User> getAll();
}