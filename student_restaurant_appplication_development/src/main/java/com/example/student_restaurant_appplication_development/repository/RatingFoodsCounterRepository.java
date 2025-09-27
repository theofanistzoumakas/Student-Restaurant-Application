package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.RatingFoodsCounter;
import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingFoodsCounterRepository extends JpaRepository<RatingFoodsCounter, Long> {
    Optional<RatingFoodsCounter> findFavouriteFoodsCounterByRestaurantFood(RestaurantFood restaurantFood);
}
