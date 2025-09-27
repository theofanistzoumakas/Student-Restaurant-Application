package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {
    Optional<Allergen> findByAllergenName(String allergenName);
}
