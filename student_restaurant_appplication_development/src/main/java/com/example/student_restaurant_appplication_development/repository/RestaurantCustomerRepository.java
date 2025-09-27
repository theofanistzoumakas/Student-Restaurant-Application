package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import com.example.student_restaurant_appplication_development.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantCustomerRepository extends JpaRepository<RestaurantCustomer, Long> {
    Optional<RestaurantCustomer> findByRestaurantUser(RestaurantUser restaurantUser);
}
