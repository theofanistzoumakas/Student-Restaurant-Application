package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.CustomerRatings;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRatingsRepository extends JpaRepository<CustomerRatings, Long> {
    Optional<CustomerRatings> findByRestaurantCustomerAndRestaurantFood(RestaurantCustomer restaurantCustomer, RestaurantFood food);
    List<CustomerRatings> findAllByRestaurantFood(RestaurantFood restaurantFood);
    Long countByRestaurantFood(RestaurantFood restaurantFood);
}
