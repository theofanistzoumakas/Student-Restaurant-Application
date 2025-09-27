package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.LunchFoods;
import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import com.example.student_restaurant_appplication_development.model.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LunchFoodsRepository extends JpaRepository<LunchFoods, Long> {
    void deleteByRestaurantMenu(RestaurantMenu restaurantMenu);
}
