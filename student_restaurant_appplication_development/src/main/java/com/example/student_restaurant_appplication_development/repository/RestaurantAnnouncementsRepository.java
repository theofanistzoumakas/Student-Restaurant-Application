package com.example.student_restaurant_appplication_development.repository;

import com.example.student_restaurant_appplication_development.model.RestaurantAnnouncements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantAnnouncementsRepository extends JpaRepository<RestaurantAnnouncements, Long> {
}
