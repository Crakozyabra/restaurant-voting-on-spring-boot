package com.example.restaurantvoting.repository;

import com.example.restaurantvoting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
