package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    Optional<User> findByEmailIgnoreCase(String email);
}