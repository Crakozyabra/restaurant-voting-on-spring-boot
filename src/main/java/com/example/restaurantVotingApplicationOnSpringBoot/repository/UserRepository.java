package com.example.restaurantVotingApplicationOnSpringBoot.repository;

import com.example.restaurantVotingApplicationOnSpringBoot.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(value = "withRoles")
    Optional<User> findByEmailIgnoreCase(String email);
}