package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {

}
