package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantFoodRepository extends JpaRepository<RestaurantFood, Long> {
    Optional<RestaurantFood> findByNameAndDescriptionAndVegetarianAndVegan(String name,String Description, Boolean vegetarian, Boolean vegan);
    void deleteById(Long foodId);
    List<RestaurantFood> findByVegetarianAndVegan(Boolean vegetarian, Boolean vegan);
}
