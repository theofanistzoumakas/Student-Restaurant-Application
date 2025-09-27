package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.Allergen;
import com.example.student_restaurant_appplication_development.model.CustomerAllergies;
import com.example.student_restaurant_appplication_development.model.RestaurantCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAllergiesRepository extends JpaRepository<CustomerAllergies, Long> {
    List<CustomerAllergies> findByRestaurantCustomer(RestaurantCustomer restaurantCustomer);
    Optional<CustomerAllergies> findByRestaurantCustomerAndAllergen(RestaurantCustomer restaurantCustomer, Allergen allergen);
    void deleteByAllergen(Allergen allergen);
    Integer countByAllergen(Allergen allergen);
}
