package com.example.student_restaurant_appplication_development.service;

import com.example.student_restaurant_appplication_development.model.RestaurantAnnouncements;
import com.example.student_restaurant_appplication_development.repository.RestaurantAnnouncementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementsService {

    private final RestaurantAnnouncementsRepository restaurantAnnouncementsRepository;

    @Autowired
    public AnnouncementsService(RestaurantAnnouncementsRepository restaurantAnnouncementsRepository) {
        this.restaurantAnnouncementsRepository = restaurantAnnouncementsRepository;
    }

    public List<RestaurantAnnouncements> viewAnnouncements() {
        return restaurantAnnouncementsRepository.findAll();
    }

    public void addAnnouncement(List<String> announcementTitle, List<String> announcementDescription) {
        for(int i = 0; i < announcementTitle.size(); i++) {
            restaurantAnnouncementsRepository.save(new RestaurantAnnouncements(announcementTitle.get(i),announcementDescription.get(i)));
        }
    }

    public void removeAnnouncement(Long announcementId) {
        restaurantAnnouncementsRepository.deleteById(announcementId);
    }
}
